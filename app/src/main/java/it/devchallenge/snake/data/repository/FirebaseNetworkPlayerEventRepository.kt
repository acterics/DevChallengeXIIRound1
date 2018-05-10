package it.devchallenge.snake.data.repository

import android.util.Log
import com.google.firebase.database.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import it.devchallenge.snake.domain.model.Direction
import it.devchallenge.snake.domain.model.PlayerEvent
import it.devchallenge.snake.domain.repository.PlayerEventRepository

class FirebaseNetworkPlayerEventRepository(private val databaseDirectionReference: DatabaseReference): PlayerEventRepository {


    override fun getPlayerEvents(): Flowable<PlayerEvent> {
        return Flowable.create( { emitter ->
            databaseDirectionReference.addValueEventListener(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    emitter.onError(error.toException())
                    databaseDirectionReference.removeEventListener(this)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.e("DEBUG", "onDataChange: $snapshot")

                    if (emitter.isCancelled) {
                        databaseDirectionReference.removeEventListener(this)
                    }
                    Direction.values().firstOrNull { it.name == "${snapshot.value}" }?.let { direction ->
                        emitter.onNext(PlayerEvent(direction, System.currentTimeMillis()))
                    }
                }

            })
        }, BackpressureStrategy.LATEST)

    }
}