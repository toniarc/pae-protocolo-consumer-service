package test;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

//https://dzone.com/articles/project-reactor-reactive-programming-with-spring-p

public class WebFluxTest {

	@Test
	public void test() {
		Flux<String> flux = Flux.generate(
			    () -> 0, 
			    (state, sink) -> {
			      sink.next("3 x " + state + " = " + 3*state); 
			      if (state == 10) sink.complete(); 
			      return state + 1; 
			    });
		
		flux.subscribe(System.out::println);
	}
	
	@Test
	void simpleFluxExample() {
	    Flux<String> fluxColors = Flux.just("red", "green", "blue");
	    fluxColors.log().subscribe(System.out::println);
	}
	
	@Test
	public void publishSubscribeExample() {
	    Scheduler schedulerA = Schedulers.newParallel("Scheduler A");
	    Scheduler schedulerB = Schedulers.newParallel("Scheduler B");
	    Scheduler schedulerC = Schedulers.newParallel("Scheduler C");
	        
	    Flux.just(1)
	        .map(i -> {
	            System.out.println("First map: " + Thread.currentThread().getName());
	            return i;
	        })
	        .subscribeOn(schedulerA)
	        .map(i -> {
	            System.out.println("Second map: " + Thread.currentThread().getName());
	            return i;
	        })
	        .publishOn(schedulerB)
	        .map(i -> {
	            System.out.println("Third map: " + Thread.currentThread().getName());
	            return i;
	        })
	        .subscribeOn(schedulerC)
	        .map(i -> {
	            System.out.println("Fourth map: " + Thread.currentThread().getName());
	            return i;
	        })
	        .publishOn(schedulerA)
	        .map(i -> {
	            System.out.println("Fifth map: " + Thread.currentThread().getName());
	            return i;
	        })
	        .blockLast();
	}
	
	@Test
	public void backpressureExample() {
	    Flux.range(1,5)
	        .subscribe(new Subscriber<Integer>() {
	            private Subscription s;
	            int counter;
	            
	            @Override
	            public void onSubscribe(Subscription s) {
	                System.out.println("onSubscribe");
	                this.s = s;
	                System.out.println("Requesting 2 emissions");
	                s.request(2);
	            }
	            
	            @Override
	            public void onNext(Integer i) {
	                System.out.println("onNext " + i);
	                counter++;
	                if (counter % 2 == 0) {
	                    System.out.println("Requesting 2 emissions");
	                    s.request(2);
	                }
	            }
	            @Override
	            public void onError(Throwable t) {
	                System.err.println("onError");
	            }
	            @Override
	            public void onComplete() {
	                System.out.println("onComplete");
	            }
				
	    });
	}
	
	@Test
	public void coldPublisherExample() throws InterruptedException {
	    Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1));
	    Thread.sleep(2000);
	    intervalFlux.subscribe(i -> System.out.println(String.format("Subscriber A, value: %d", i)));
	    Thread.sleep(2000);
	    intervalFlux.subscribe(i -> System.out.println(String.format("Subscriber B, value: %d", i)));
	    Thread.sleep(3000);
	}
	
	@Test
	public void hotPublisherExample() throws InterruptedException {
	    Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1));
	    ConnectableFlux<Long> intervalCF = intervalFlux.publish();
	    intervalCF.connect();
	    Thread.sleep(2000);
	    intervalCF.subscribe(i -> System.out.println(String.format("Subscriber A, value: %d", i)));
	    Thread.sleep(2000);
	    intervalCF.subscribe(i -> System.out.println(String.format("Subscriber B, value: %d", i)));
	    Thread.sleep(3000);
	}
}
