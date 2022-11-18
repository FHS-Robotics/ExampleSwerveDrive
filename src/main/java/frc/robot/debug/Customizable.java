package frc.robot.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Customizable<T extends Object> {
  private List<Consumer<T>> mConsumers = new ArrayList<>();
  private T mValue;

  public Customizable(T initialValue) {
    mValue = initialValue;
  }

  public T get() {
    return mValue;
  }

  public void consume(Consumer<T> consumer) {
    consumer.accept(mValue);
    mConsumers.add(consumer);
  }

  public void set(T newValue) {
    mValue = newValue;
    for (Consumer<T> consumer : mConsumers) {
      consumer.accept(newValue);
    }
  }
}
