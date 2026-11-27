package travel.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandHistory {
    private final Deque<TripCommand> history = new ArrayDeque<>();

    public void executeCommand(TripCommand command) {
        command.execute();
        history.push(command);
    }

    public boolean undoLast() {
        if (history.isEmpty()) {
            return false;
        }
        TripCommand last = history.pop();
        last.undo();
        return true;
    }

    public int size() {
        return history.size();
    }
}