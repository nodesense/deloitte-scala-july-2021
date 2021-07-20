object S005_Trait extends  App {
  // Trait is similar to Java Interface
  // Trait is an abstraction without implementation
  // Trait is used for polymorphic behaviors
  // member declaration (no definition), also provide default member implementation
  // We cannot create instance of Trait as Trait is not complete class

  // Vehicle itself is an abstraction
  trait Vehicle {
    def start() // no method implementation, only declaration
    def stop()
    def is_running(): Boolean
  }

  // Vehicle trait is forcing a class to implement the Trait members
  class HondaActiva extends Vehicle {
    private var isRunning = false;

    def start() = {
      println("activa started")
      isRunning = true
    }

    def stop() = {
      println("activa stopped")
      isRunning = false
    }

    def is_running() = {
      isRunning
    }

    def break() = {
      println("Apply Break in activa")
    }
  }

  class SuzukiDesire extends Vehicle {
    private var isRunning = false;

    def start() = {
      println("Desire started")
      isRunning = true
    }

    def stop() = {
      println("Desire stopped")
      isRunning = false
    }

    def is_running() = {
      isRunning
    }
  }

  val activa1 = new HondaActiva()
  activa1.start()

  // allowed, Activa is a vehicle
  // we are assigning activa1 object reference to vehicle reference
  val vehicle: Vehicle = activa1
  println("is running ", vehicle.is_running())
  vehicle.stop() // turning off activa1 , vehicle is reference of activa1
  println("is running ", activa1.is_running())

  // vehicle.break() // won't work, break is not part of the trait Vehicle

}
