package reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        Mono.just(1)
                .doOnSuccess(System.out::println)
                .subscribe();

        Mono.justOrEmpty(Optional.empty())
                .defaultIfEmpty("Empty")
                .doOnSuccess(System.out::println)
                .subscribe();

        Mono.justOrEmpty(2)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new Exception())))
                .doOnSuccess(System.out::println)
                .subscribe();

        Mono.justOrEmpty(5)
                .map(n -> n * n)
                .doOnSuccess(System.out::println)
                .subscribe();

        Mono.justOrEmpty(69)
                .map(String::valueOf)
                .doOnSuccess(n -> System.out.println(n.getClass()))
                .subscribe();

        Mono.justOrEmpty(5)
                .filter(n -> (n & 1) == 0)
                .map(String::valueOf)
                .defaultIfEmpty("is Even")
                .doOnSuccess(System.out::println)
                .subscribe();

        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Mono.justOrEmpty(numbers)
                .doOnSuccess(arr -> System.out.println(arr.getClass()))
                .subscribe(arr -> {
                    for (int n : arr) {
                        if ((n & 1) == 0) {
                            System.out.println(n);
                        }
                    }
                });

        Flux.fromIterable(List.of(1, 2, 3, 4, 5))
                .map(n -> n * n)
                .doOnNext(System.out::println)
                .subscribe();

        Flux.fromIterable(List.of(1, 2, 3, 4, 5))
                .map(n -> n * n)
                .collectList()
                .doOnNext(System.out::println)
                .subscribe();

        Flux.fromStream(Arrays.stream(numbers).boxed())
                .filter(n -> (n & 1) == 0)
                .collectList()
                .subscribe(System.out::println);

        Flux.fromIterable(List.of(1, 2, 3, 4, 5))
                .collectMap(
                        number -> String.format("%04d", Integer.parseInt(Integer.toBinaryString(number))),
                        number -> number
                )
                .doOnSuccess(map -> System.out.println("Map criado: " + map)) // Exibe o mapa criado
                .subscribe();

    }
}