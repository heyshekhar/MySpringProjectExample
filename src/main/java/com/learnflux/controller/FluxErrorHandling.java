package com.learnflux.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.reactivestreams.Publisher;

import com.sun.media.jfxmedia.events.NewFrameEvent;

import reactor.core.publisher.Flux;

public class FluxErrorHandling {

	public static void main(String[] args) {
		
		
		
		
		fluxErrorExample();

	}

	private static void fluxErrorExample() {
		List<String> str = new ArrayList<>();
		str.add("shekhar");
		str.add("kumar");
		str.add("rathore");
		
		Flux<String> val = Flux.fromIterable(str)
		.map( s ->{
			if(s.equalsIgnoreCase("kumar")) {
				throw new RuntimeErrorException(null, "kumar");
			}
			return s;
				})
					  .onErrorResume(e -> { throw new RuntimeErrorException(null, "default"); })
					 ;
		
		val.onErrorStop()
		.
		doOnError(e -> {
			System.out.println("error "+e);
		})
		.subscribe(s -> {
			System.out.println("subscribe "+s);
		});
		
	}

}
