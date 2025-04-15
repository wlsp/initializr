package wlsp.tech.initializr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wlsp.tech.initializr.model.Message;
import wlsp.tech.initializr.model.MessageNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final List<Message> messages = new ArrayList<>(
            List.of(
                    new Message("1", "Alice", "Hallo Welt!"),
                    new Message("2", "Bob", "Servus zusammen!")
            )
    );

    @GetMapping
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @PostMapping
    public ResponseEntity<Message> createdMessage(@RequestBody Message message) {
        messages.add(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Message>> getMessageById(@PathVariable String id) throws MessageNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(Optional.ofNullable(messages.stream()
                .filter(m -> m.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new MessageNotFoundException("Message with id " + id + " not found!"))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable String id){
        Optional<Message>  message = Optional.ofNullable(messages.stream()
                .filter(m -> m.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new MessageNotFoundException("Could not remove Message with id. The message with the id: "
                        + id + " does not exist!")));

        message.ifPresent(messages::remove);
        return ResponseEntity.ok("The message with id " + id + " was successfully removed!");
    }

    @ExceptionHandler(MessageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(MessageNotFoundException ex) {
        return Map.of("err", ex.getMessage());
    }
}
