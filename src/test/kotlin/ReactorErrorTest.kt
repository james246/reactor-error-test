import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono

class ReactorErrorTest {
    @Test
    fun onErrorResume() {
        val x = Mono.error<String>(Exception())

        x.onErrorResume { e ->
            println("[Publisher] onError: $e")
            Mono.just("Fallback result")
        }.subscribe(
            { item -> println("[Subscriber] onNext: $item") },
            { e -> println("[Subscriber] onError: $e") },
            { println("[Subscriber] onComplete") }
        )
    }
    /*
    [Publisher] onError: java.lang.Exception
    [Subscriber] onNext: Fallback result
    [Subscriber] onComplete
    */

    @Test
    fun onErrorMap() {
        val x = Mono.error<String>(Exception())

        x.onErrorMap { e ->
            println("[Publisher] onError: $e")
            Exception("New exception")
        }.subscribe(
            { item -> println("[Subscriber] onNext: $item") },
            { e -> println("[Subscriber] onError: $e") },
            { println("[Subscriber] onComplete") }
        )
    }
    /*
    [Publisher] onError: java.lang.Exception
    [Subscriber] onError: java.lang.Exception: New exception
     */

    @Test
    fun doOnError() {
        val x = Mono.error<String>(Exception())

        x.doOnError { e ->
            println("[Publisher] onError: $e")
        }.subscribe(
            { item -> println("[Subscriber] onNext: $item") },
            { e -> println("[Subscriber] onError: $e") },
            { println("[Subscriber] onComplete") }
        )
    }
    /*
    [Publisher] onError: java.lang.Exception
    [Subscriber] onError: java.lang.Exception
     */

    @Test
    fun onErrorContinue() {
        val x = Mono.error<String>(Exception())

        x.onErrorContinue { e, item ->
            println("[Publisher] Error caused by element $item")
            println("[Publisher] onError: $e")
        }.subscribe(
            { item -> println("[Subscriber] onNext: $item") },
            { e -> println("[Subscriber] onError: $e") },
            { println("[Subscriber] onComplete") }
        )
    }
    /*
    [Subscriber] onError: java.lang.Exception
     */

    @Test
    fun onErrorReturn() {
        val x = Mono.error<String>(Exception())

        x.onErrorReturn("onErrorReturn fallback result")
            .subscribe(
                { item -> println("[Subscriber] onNext: $item") },
                { e -> println("[Subscriber] onError: $e") },
                { println("[Subscriber] onComplete") }
            )
    }
    /*
    [Subscriber] onNext: onErrorReturn fallback result
    [Subscriber] onComplete
     */
}
