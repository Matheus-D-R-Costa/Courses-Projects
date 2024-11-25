import EventInterface from "./event-interface";
import EventHandlerInterface from "./event-interface-handler";

export default interface EventDispatcherInterface {

    notify(event: EventInterface): Promise<void>
    register(eventName: string, eventHandler: EventHandlerInterface): void
    unregister(eventName: string, eventHandler: EventHandlerInterface): void
    unregisterAll(): void

}