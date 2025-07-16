package com.test.websocket.publisher;

/**
 * Interface for publishing messages to a specified destination.
 */
public interface MessagePublisher {
    /**
     * Publishes a message to the given destination.
     *
     * @param destination the destination to which the message will be sent
     * @param message     the message to be published
     */
    void publish(String destination, Object message);
}
