package com.codecool.termlib;

public class Terminal {
    /**
     * The beginning of control sequences.
     */
    // HINT: In \033 the '0' means it's an octal number. And 33 in octal equals 0x1B in hexadecimal.
    // Now you have some info to decode that page where the control codes are explained ;)
    private static final String CONTROL_CODE = "\033[";
    /**
     * Command for whole screen clearing.
     *
     * Might be partitioned if needed.
     */
    private static final String CLEAR = "2J";
    /**
     * Command for moving the cursor.
     */
    private static final String MOVE = "H";
    /**
     * Command for printing style settings.
     *
     * Handles foreground color, background color, and any other
     * styles, for example color brightness, or underlines.
     */
    private static final String STYLE = "m";

    /**
     * Reset printing rules in effect to terminal defaults.
     *
     * Reset the color, background color, and any other style
     * (i.e.: underlined, dim, bright) to the terminal defaults.
     */
    public void resetStyle() {
        int attrCode = 0;
        this.command(String.format("%s%d%s", CONTROL_CODE, attrCode, STYLE));
    }

    /**
     * Clear the whole screen.
     *
     * Might reset cursor position.
     */
    public void clearScreen() {
        this.command(String.format("%s%s%s%s", CONTROL_CODE, MOVE, CONTROL_CODE, CLEAR));
    }

    /**
     * Move cursor to the given position.
     *
     * Positions are counted from one.  Cursor position 1,1 is at
     * the top left corner of the screen.
     *
     * @param x Column number.
     * @param y Row number.
     */
    public void moveTo(Integer x, Integer y) {
        this.command(String.format("%s%d;%d%s", CONTROL_CODE, x, y, MOVE));
    }

    /**
     * Set the foreground printing color.
     *
     * Already printed text is not affected.
     *
     * @param color The color to set.
     */
    public void setColor(Color color) {
        int colorCode = color.getAsInt() + 30;
        this.command(String.format("%s%d%s", CONTROL_CODE, colorCode, STYLE));
    }

    /**
     * Set the foreground printing color.
     *
     * Already printed text is not affected.
     *
     * @param fontColor The color to set.
     */
    public void setFontColor(RGB fontColor) {
        this.command(String.format("%s38;2;%d;%d;%dm", CONTROL_CODE, fontColor.red, fontColor.green, fontColor.blue));
    }

    /**
     * Set the background printing color.
     *
     * Already printed text is not affected.
     *
     * @param fontColor The color to set .
     */
    public void setBackgroundColor(RGB fontColor) {
        this.command(String.format("%s48;2;%d;%d;%dm", CONTROL_CODE, fontColor.red, fontColor.green, fontColor.blue));
    }

    /**
     * Set the background printing color.
     *
     * Already printed text is not affected.
     *
     * @param color The background color to set.
     */
    public void setBgColor(Color color) {
        int colorCode = color.getAsInt() + 40;
        this.command(String.format("%s%d%s", CONTROL_CODE, colorCode, STYLE));
    }

    /**
     * Make printed text underlined.
     *
     * On some terminals this might produce slanted text instead of
     * underlined.  Cannot be turned off without turning off colors as
     * well.
     */
    public void setUnderline() {
        int attrCode = 4;
        this.command(String.format("%s%d%s", CONTROL_CODE, attrCode, STYLE));
    }

    public int moveCount;
    /**
     * Move the cursor relatively.
     *
     * Move the cursor amount from its current position in the given
     * direction.
     *
     * @param direction Step the cursor in this direction.
     * @param amount Step the cursor this many times.
     */
    public void moveCursor(Direction direction, Integer amount) {
        switch (direction) {
            case UP:
                this.command(String.format("%s%dA", CONTROL_CODE, amount));
                break;
            case DOWN:
                this.command(String.format("%s%dB", CONTROL_CODE, amount));
                break;
            case FORWARD:
                this.command(String.format("%s%dC", CONTROL_CODE, amount));
                break;
            case BACKWARD:
                this.command(String.format("%s%dD", CONTROL_CODE, amount));
                break;
        }

        moveCount += amount;
    }

    /**
     * Set the character diplayed under the current cursor position.
     *
     * The actual cursor position after calling this method is the
     * same as beforehand.  This method is useful for drawing shapes
     * (for example frame borders) with cursor movement.
     *
     * @param c the literal character to set for the current cursor
     * position.
     */
    public void setChar(char c) {
        this.command(String.valueOf(c));
        this.moveCursor(Direction.BACKWARD, 1);
    }

    /**
     * Set the character displayed under the current cursor position.
     *
     * The actual cursor position after calling this method is the
     * same as beforehand.  This method is useful for drawing shapes
     * (for example frame borders) with cursor movement.
     *
     * @param c the literal character to set for the current cursor
     * position.
     */
    public void set(char c, RGB fontColor, RGB backgroundColor) {
        this.setFontColor(fontColor);
        this.setBackgroundColor(backgroundColor);
        this.setChar(c);
        this.resetStyle();
    }

    /**
     * Helper function for sending commands to the terminal.
     *
     * The common parts of different commands shall be assembled here.
     * The actual printing shall be handled from this command.
     *
     * @param commandString The unique part of a command sequence.
     */
    private void command(String commandString) {
        System.out.print(commandString);
    }
}
