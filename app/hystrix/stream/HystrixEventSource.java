package hystrix.stream;

import play.libs.F.Callback0;
import play.mvc.Results.Chunks;

public class HystrixEventSource extends Chunks<String> {
	
	private Chunks.Out<String> out;
	
	private Boolean isDisconnected = false;

	/**
	 * Create a new EventSource socket
	 *
	 */
	public HystrixEventSource() {
		super(play.core.j.JavaResults.writeString("text/event-stream", play.api.mvc.Codec.javaSupported("utf-8")));
	}

	public void onReady(Chunks.Out<String> out) {
		this.out = out;
		this.out.onDisconnected(new Callback0() {

			@Override
			public void invoke() throws Throwable {
				isDisconnected = true;
			}
		});
	}

	public boolean isDisconnected() {
		return this.isDisconnected;
	}
	
	public void send(String data) {
		if(out != null) {
			out.write(data);				
		}
	}

}
