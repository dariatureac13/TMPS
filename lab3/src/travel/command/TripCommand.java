package travel.command;

public interface TripCommand {
    void execute();
    void undo();
    String getDescription();
}