public class OneLaneBridge extends Bridge {
  protected int bridgeLimit;

  public OneLaneBridge(int bridgeLimit) {
    this.bridgeLimit = bridgeLimit;
  }

  @Override
  public synchronized void arrive(Car car) throws InterruptedException {
    while (bridge.size() >= bridgeLimit || (bridge.size() > 0 && direction != car.getDirection())) {
      // Too many cars on bridge, or it's the wrong direction
      wait();
    }

    // If there's no one on the bridge, then we can set the direction
    if (bridge.size() == 0)
      direction = car.getDirection();

    // Set time, add to bridge, print, increment time
    car.setEntryTime(currentTime);
    bridge.add(car);
    System.out.println(this);
    currentTime += 1;
  }

  @Override
  public synchronized void exit(Car car) throws InterruptedException {
    while (bridge.get(0) != car) {
      // Not the first car, have to wait
      wait();
    }

    // Remove the car, notify, print
    bridge.remove(car);

    notifyAll();

    System.out.println(this);
  }

  @Override
  public String toString() {
    return "Bridge (dir=" + direction + "): " + bridge.toString();
  }
}
