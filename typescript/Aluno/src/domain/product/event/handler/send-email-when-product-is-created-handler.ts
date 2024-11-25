import ProductCreatedEvent from "../product-created-event"
import EventHandlerInterface from "../../../@shared/event/event-interface-handler"

export default class SendEmailWhenProductIsCreatedHandler implements EventHandlerInterface<ProductCreatedEvent> {
    
    handle(event: ProductCreatedEvent): Promise<void> { // enviando para o rabbitMQ...
        console.log("Sending email to customer...");
        return Promise.resolve();
    }

}