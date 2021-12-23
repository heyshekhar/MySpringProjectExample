package com.learnflux.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.learnflux.dto.ResponseBodyHolder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

public class FluxExample {

	public static void main(String[] args) {


//		fluxExampleOne();

//		fluxExampleList();
		
//		fluxExampleThread();
		
		fluxExampleMap();
		
//		fluxExampleFlatMap();
		
//		fluxExampleFlatMapAsync();
		
//		multipleExecutorService();
	}

	private static void multipleExecutorService() {
		 ExecutorService myPool = Executors.newFixedThreadPool(10);
		 ExecutorService myPool1 = Executors.newFixedThreadPool(10);
		
	}

	private static void fluxExampleFlatMapAsync() {
		 ExecutorService myPool = Executors.newFixedThreadPool(10);
			List<String> str = new ArrayList<>();
			str.add("spring");
			str.add("webflux");
			str.add("example");
			str.add("shekhar");
			str.add("kumar");
			str.add("rathore");
			
			
			AtomicInteger num = new AtomicInteger();
			 Flux<ResponseBodyHolder> name = Flux.fromIterable(str)
//						.log()
						.window(3)
						.flatMapSequential(s -> {
							System.out.println("parallel flux : "+s);
							return s.map( d -> {
								ResponseBodyHolder  bodyHolder = new ResponseBodyHolder();
								if(d.equalsIgnoreCase("spring")){
									System.out.println("parallel flux : "+s);
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									
									bodyHolder.setName(d+num.incrementAndGet());
								}else {
									bodyHolder.setName(d);
								}
								return Stream.of(bodyHolder);
							}).subscribeOn(Schedulers.parallel())
									.flatMap( r -> Flux.fromStream(r));
							
						});
						
						
						name.subscribe(s -> {
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					    	System.out.println("value "+s.getName()+" thread : "+Thread.currentThread().getName());
					    });;
			
			
			
			
			
			
			
			
//		 Flux<ResponseBodyHolder> name = Flux.fromIterable(str)
////				.log()
//				.window(10)
//				.flatMapSequential(s -> {
//					return s.map( d -> {
//						ResponseBodyHolder  bodyHolder = new ResponseBodyHolder();
//						if(d.equalsIgnoreCase("spring")){
//							System.out.println("parallel flux : "+s);
//							try {
//								Thread.sleep(1000);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//							
//							bodyHolder.setName(d+num.incrementAndGet());
//						}else {
//							bodyHolder.setName(d);
//						}
//						return Stream.of(bodyHolder);
//					}).subscribeOn(Schedulers.parallel())
//							.flatMap( r -> Flux.fromStream(r));
//					
//				});
//				
//				
//				name.subscribe(s -> {
//			    	System.out.println("value "+s.getName()+" thread : "+Thread.currentThread().getName());
//			    });;
		
	}

	private static void fluxExampleMap() {
		ExecutorService myPool = Executors.newFixedThreadPool(10);
		List<String> str = new ArrayList<>();
		AtomicInteger num = new AtomicInteger();
		
		for(int i= 1; i<30; i++) {
			str.add("s "+num.incrementAndGet());
		}
		
		System.out.println("number odf processor : "+Runtime.getRuntime().availableProcessors());
		Flux<ResponseBodyHolder> names = Flux.fromIterable(str)
				.parallel(10)
				.runOn(Schedulers.fromExecutorService(myPool))
//				.parallel()
//				.runOn(Schedulers.parallel())
				.map(s -> {
					ResponseBodyHolder  bodyHolder = new ResponseBodyHolder();
//					if(s.equalsIgnoreCase("s 1")){
						
						try {
							Thread.sleep(1000);
							System.out.println("in parallel flux : "+new Date().getSeconds()+" : "+s);
//							System.out.println("out parallel flux : "+s);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						bodyHolder.setName(s);
//					}else {
//						bodyHolder.setName(s);
//					}
					return bodyHolder;
				}
					
				).sequential()
				;
		
		
	    names.subscribe(s -> {
	    	
//	    	System.out.println("value "+s.getName()+" thread : "+Thread.currentThread().getName());
	    	
	    });
	    try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("checking thread shutdown or not : "+myPool.isShutdown());
	}

	private static void fluxExampleFlatMap() {
		 ExecutorService myPool = Executors.newFixedThreadPool(10);
		List<String> str = new ArrayList<>();
		str.add("spring");
		str.add("webflux");
		str.add("example");
		str.add("shekhar");
		str.add("kumar");
		str.add("rathore");
		
		
		AtomicInteger num = new AtomicInteger();
		try {
		ParallelFlux<ResponseBodyHolder> name = Flux.fromIterable(str)
				.log()
				.parallel(3)
				.runOn(Schedulers.fromExecutorService(myPool))
				.flatMap(s -> {
					ResponseBodyHolder  bodyHolder = new ResponseBodyHolder();
					if(s.equalsIgnoreCase("spring")){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
//						System.out.println("parallel flux : "+s);
						bodyHolder.setName(s+" "+num.incrementAndGet());
					}else {
						bodyHolder.setName(s);
					}
					return Flux.fromStream(Stream.of(bodyHolder));
				});
				
				
				name.subscribe(s -> {
			    	System.out.println("value "+s.getName()+" thread : "+Thread.currentThread().getName());
			    });;
			    
		}finally{
//			System.out.println("checking thread terminated : "+myPool.);
				try {
					// wait 1 second for closing all threads
					myPool.shutdown();
					System.out.println("thread is shutdown : "+myPool.isShutdown());
				} catch (Exception e) {
					Thread.currentThread().interrupt();
				}
			System.out.println("thread is shutdown : "+myPool.isShutdown());
		}
		
	}

	private static void fluxExampleThread() {
		List<String> str = new ArrayList<>();
		str.add("spring");
		str.add("webflux");
		str.add("example");
		
		AtomicInteger num = new AtomicInteger();
		
		ParallelFlux<Object> names = Flux.fromIterable(str)
				.log()
				.parallel(2)
				.runOn(Schedulers.boundedElastic())
				.map( s-> {
					
					if(s.equalsIgnoreCase("webflux")) {
						try {
							System.out.println("waiting...");
							Thread.sleep(1000);
							System.out.println("done...");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return s+" "+num.incrementAndGet();
					
				});
		
	    names.subscribe(s -> {
	    	System.out.println("value "+s+" thread : "+Thread.currentThread().getName());
	    });
		
	}

	private static void fluxExampleList() {
		List<String> str = new ArrayList<>();
		str.add("shekhar");
		str.add("kumar");
		str.add("rathore");
		
		AtomicInteger num = new AtomicInteger();
		
		Flux<String> names = Flux.fromIterable(str)
				.map( s-> {
					return s+" "+num.incrementAndGet();
				});
		
	    names.subscribe(System.out::println);
		
	}

	private static void fluxExampleOne() {
		
		Flux<String> names = Flux.just("Arindam","Paul");
	    names.subscribe(System.out::println);
		
	}

}
