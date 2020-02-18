/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * A class to configuraly defer the creation of something untill needed
 *
 * @param <T> The thing to be defered
 */
public class Defered<T> {

    private T instance = null;
    private final Supplier<T> provider;
    private final Consumer<T> consumer;

    /**
     * Let's you create a Defered<T> without a Consumer<T>
     *
     * @param name     Name of Shuffleboard field
     * @param provider Function to call for creation
     */
    public Defered(final String name, final Supplier<T> provider) {
        this(name, provider, (sub) -> {
        });
    }

    /**
     * Create a Defered<T> with a Consumer<T>
     * 
     * @param name
     * @param provider
     * @param consumer
     */
    public Defered(final String name, final Supplier<T> provider, final Consumer<T> consumer) {
        this.provider = provider;
        this.consumer = consumer;

        boolean isEnabled = Shuffleboard.getTab("Test").add(name + " Enabled", true).getEntry().getBoolean(true);

        if (isEnabled && instance == null)
            enable();
    }

    public void enable() {
        instance = provider.get();
        consumer.accept(instance);
    }

    public T get() {
        if (instance == null)
            enable();
        return instance;
    }
}
