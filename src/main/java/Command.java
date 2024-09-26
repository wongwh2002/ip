public enum Command {
    BYE, LIST, TODO, DEADLINE, EVENT, UNMARK, MARK, DELETE;

    public static boolean isLegalCommand(String command) {
        try {
            Command.valueOf(command.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
