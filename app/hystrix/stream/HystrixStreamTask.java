package hystrix.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsPoller;

public class HystrixStreamTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(HystrixStreamTask.class);
	
	private final MetricJsonListener jsonListener;
	private final HystrixMetricsPoller poller;
	private HystrixEventSource eventSource;
	private int delay;
	
	public HystrixStreamTask(HystrixEventSource eventSource, int delay) {
		this.eventSource = eventSource;
		this.delay = delay;
		jsonListener = new MetricJsonListener();
		poller = new HystrixMetricsPoller(jsonListener, delay);
		poller.start();
	}

	@Override
	public void run() {

		try {
			while (poller.isRunning() && !eventSource.isDisconnected() ) {

				List<String> jsonMessages = jsonListener.getJsonMetrics();
				if (jsonMessages.isEmpty()) {
					eventSource.send("ping: \n\n");
				} else {
					for (String json : jsonMessages) {
						eventSource.send("data: " + json + "\n\n");
					}
				}
				Thread.sleep(delay);
			}

		} catch (InterruptedException e) {
			poller.shutdown();
			logger.debug("InterruptedException. Will stop polling.");
			Thread.currentThread().interrupt();

		} catch (Exception e) {
			poller.shutdown();
			logger.error("Failed to write. Will stop polling.", e);
			
		} finally {
			poller.shutdown();
			logger.debug("Stopping Turbine stream to connection");	
		}
		
	}
	
	
	
	
	
	/**
     * This will be called from another thread so needs to be thread-safe.
     * @ThreadSafe
     */
    private static class MetricJsonListener implements HystrixMetricsPoller.MetricsAsJsonPollerListener {

        /**
         * Setting limit to 1000. In a healthy system there isn't any reason to hit this limit so if we do it will throw an exception which causes the poller to stop.
         * <p>
         * This is a safety check against a runaway poller causing memory leaks.
         */
        private final LinkedBlockingQueue<String> jsonMetrics = new LinkedBlockingQueue<String>(1000);

        /**
         * Store JSON messages in a queue.
         */
        @Override
        public void handleJsonMetric(String json) {
            jsonMetrics.add(json);
        }

        /**
         * Get all JSON messages in the queue.
         * 
         * @return
         */
        public List<String> getJsonMetrics() {
            ArrayList<String> metrics = new ArrayList<String>();
            jsonMetrics.drainTo(metrics);
            return metrics;
        }
    }
	

}
