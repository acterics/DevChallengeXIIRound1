package it.devchallenge.snake.common.extension.rx

import io.reactivex.FlowableOperator
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class FlowableUnmodifiedScan<T>(private val accumulator: (previous: T, next: T) -> T):
        FlowableOperator<T, T> {
    override fun apply(observer: Subscriber<in T>): Subscriber<in T> {
        return object: Subscriber<T> {

            var value: T? = null
            var subscription: Subscription? = null


            override fun onComplete() {
                observer.onComplete()
            }

            override fun onSubscribe(s: Subscription?) {
                subscription = s
                observer.onSubscribe(s)
            }

            override fun onNext(t: T) {
                val current = value
                current?.let {
                    try {
                        observer.onNext(accumulator(it, t))
                    } catch (e: Throwable) {
                        subscription?.cancel()
                        onError(e)
                        return
                    }
                } ?: run {
                    observer.onNext(t)
                }
                value = t
            }

            override fun onError(t: Throwable?) {
                observer.onError(t)
            }
        }
    }


}