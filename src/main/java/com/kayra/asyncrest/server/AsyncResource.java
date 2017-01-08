package com.kayra.asyncrest.server;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import org.glassfish.jersey.server.ChunkedOutput;

@Path("/async")
public class AsyncResource {

	@GET
	public void asyncGet(@Suspended final AsyncResponse asyncResponse) {
		System.out.println("started method" + new Date());
		new Thread(new Runnable() {
			public void run() {
				String result = veryExpensiveOperation();
				asyncResponse.resume(result);
			}

			private String veryExpensiveOperation() {
				// ... very expensive operation
				try {
					Thread.sleep(2000);
					System.out.println("async ended:" + new Date());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return "result";
			}
		}).start();
		System.out.println("finished method:" + new Date());
	}

	@GET
	@Path("/chunked")
	public ChunkedOutput<String> getChunkedResponse() {
		System.out.println("started method:" + new Date());
		final ChunkedOutput<String> output = new ChunkedOutput<>(String.class);

		new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);
					output.write("result");
					System.out.println("async ended:" + new Date());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		System.out.println("finished method:" + new Date());
		return output;
	}
}
