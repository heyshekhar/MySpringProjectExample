package com.learnflux.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
//import org.apache.commons.math3.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnflux.dto.ParelleResponceBody;
import com.learnflux.dto.RequestHolder;
import com.learnflux.dto.ResponseBodyHolder;

import javafx.util.Pair;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/flux")
public class RequestHandler {
	
	Flux<Map<String, Integer>> val;
	
	@GetMapping(value = "/welcome")
	public String welcomeMessage() {
		return "flux example";
	}
	
	@GetMapping(value = "/example/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
//	@GetMapping(value = "/example/{id}")
	public Flux<Object> fluxExample(@PathVariable("id") Integer id) {
		
		
		List<String> str = new ArrayList<>();
		str.add("spring");
		str.add("flux");
		str.add("testing");
		
		System.out.println("start time : " +(new Date().getSeconds()));
		
		
//		Flux<Map<String, Integer>> output = convertListToMap(str);
		
		
//		str.stream().forEach(s -> {
//			 try {
//				val = Flux.just(convertListToMap(s));
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
//		Flux<Map<Integer, String>> fluxString = Flux.just(convertListToMap(str))
//				.delayElements(Duration.ofMillis(2))
//				.log();
		
//		Flux<String> output = Flux.fromIterable(str)
//				.delayElements(Duration.ofSeconds(2))
//		.log();
////		
//		output.subscribe(System.out::println);
		
		
		
//		Flux<Map<String, Integer>> output = Flux.fromIterable(str)
//		.map(this::convertListToMap);
		ObjectMapper oMapper = new ObjectMapper();
		
		  Flux<Object> output = Flux.fromIterable(str)
				.map(s -> {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Map<String, Integer> strMap = new HashMap<>();
					AtomicInteger val = new AtomicInteger();
					
					strMap.put(s, val.incrementAndGet());
//					str.stream().forEach( s -> {
//						strMap.put(val.incrementAndGet(), s);
////						System.out.println(val);
//					});
					System.out.println(strMap);
					return strMap;
				});
		
//		.map(this::convertListToMap);
//		 Map<String, Integer> value = oMapper.convertValue(output, HashMap.class);
//		 Map<String, Integer> value
//		 output.subscribe(s -> {
//			val = oMapper.convertValue(s, HashMap.class);
//			 System.out.println(value);
//			 System.out.println(s);
//		 });
//		 System.out.println(value);
		
//		Flux<String> arrayOutput = Flux.fromIterable(str).delayElements(Duration.ofMillis(5000)).log();
									
		
		
		System.out.println("End time : " +(new Date().getSeconds()));
		return output;
		
//		return "flux example";
	}
	
	@GetMapping(value = "/parallelFluxexample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public ParallelFlux<ParelleResponceBody> convertListToMap() {
		ExecutorService myPool = Executors.newFixedThreadPool(10);
		List<String> str = new ArrayList<>();
		str.add("shekhar");
		str.add("kumar");
		str.add("rathore");

		System.out.println("start testing parallel flux "+str);
//		ParallelFlux<ParelleResponceBody> check = Flux.fromIterable(str)
//				.parallel(3)
//				.runOn(Schedulers.parallel())
//				.map( s -> {
//					ParelleResponceBody body = new ParelleResponceBody();
//			 body.setStr(convertListToMap(s));
//			 return body;
//		});
		
		try {

			ParallelFlux<ParelleResponceBody> name = Flux.fromIterable(str).parallel(3)
					.runOn(Schedulers.fromExecutorService(myPool)).flatMap(s -> {
//					System.out.println("parallel flux "+s);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ParelleResponceBody bodyHolder = new ParelleResponceBody();
						bodyHolder.setStr(s);
						return Flux.fromStream(Stream.of(bodyHolder));
					});
				name.subscribe(s -> {
				System.out.println("parallel output : " + s.getStr());
			});
			return name;
		} finally {
			System.out.println("checking thread : " + myPool.isShutdown());
				if (myPool.isTerminated()) {
					myPool.shutdown();
				}
				System.out.println("checking thread : " + myPool.isShutdown());

		}
		
//		return new ResponseEntity<ParallelFlux<ParelleResponceBody>>(name,HttpStatus.INTERNAL_SERVER_ERROR);
		
		
//		Flux<Map<String, Integer>> output = Flux.just(convertListToMap(Flux.fromIterable(str)))
//				.delayElements(Duration.ofSeconds(2))
//		.log();
		
		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Map<Integer, String> strMap = new HashMap<>();
//		AtomicInteger val = new AtomicInteger();
//		str.stream().forEach( s -> {
//			strMap.put(val.incrementAndGet(), s);
////			System.out.println(val);
//		});
		
//		return output;
	}
	

	@PostMapping(value = "/postfluxexample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	private Flux<ResponseBodyHolder> getPostResponse(@RequestParam(required = false) String networkName, @RequestBody String str) {
		ObjectMapper oMapper = new ObjectMapper();
		List<String> strVal = new ArrayList<>();
		AtomicInteger val = new AtomicInteger();
//		strVal = str.getStrList();
		
	Flux<ResponseBodyHolder> responseObject = Flux.fromIterable(strVal)
					.map(s -> {
						ResponseBodyHolder responseBody = new ResponseBodyHolder();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						Map<String, Integer> strMap = new HashMap<>();
//						Pair<String, Integer> p;
////						strMap.put(s.toString(), val.incrementAndGet());
//						p = new Pair<>(s.toString(), val.incrementAndGet());
//						str.stream().forEach( s -> {
//							strMap.put(val.incrementAndGet(), s);
////							System.out.println(val);
//						});
//						System.out.println("map : "+strMap);
//						responseBody.setMapResponse(strMap);
						responseBody.setName(s.toString());
						responseBody.setBooleanValue(true);
						return responseBody;
					});
		 
		 
		 
		 responseObject.subscribe(s -> {
				 System.out.println("flux testing reposnse object : "+s.getName()+"----"+s.getBooleanValue());
//				 System.out.println(s);
			 });
//		System.out.println("value from post request : "+str);
		return responseObject;
	}
	
	
	

	private Map<String, Integer> convertListToMap(Flux<String> fromIterable) {
		
		Iterable<String> v = fromIterable.toIterable();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Integer> strMap = new HashMap<>();
		AtomicInteger val = new AtomicInteger();
		strMap.put(String.valueOf(fromIterable), val.incrementAndGet());
		return strMap;
	}

	private String convertListToMap(String str) {
		
		try {
			if (str.equalsIgnoreCase("shekhar")) {
				callForLoop(str);
			} else {
				System.out.println("waiting : "+str+"`````"+new Date().getSeconds());
				System.out.println("thread : " +Thread.currentThread());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Map<String, Integer> strMap = new HashMap<>();
//		AtomicInteger val = new AtomicInteger();
//		
//		strMap.put(str, val.incrementAndGet());
////		str.stream().forEach( s -> {
////			strMap.put(val.incrementAndGet(), s);
//////			System.out.println(val);
////		});
//		System.out.println(strMap);
//		return strMap;
		
		return str;
		
		
	}

	private void callForLoop(String str) {
		for (int i = 1; i <= 100000; i++) {
			for (int j = 1; j <= 10000; j++) {
				if (i == 99999) {
					System.out.println("waiting : " + str + "`````" + new Date().getSeconds());
					System.out.println("thread : " +Thread.currentThread());
					break;
				} 
			}
			
		}
		
	}
}
