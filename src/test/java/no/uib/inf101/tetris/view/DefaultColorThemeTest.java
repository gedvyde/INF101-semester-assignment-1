package no.uib.inf101.tetris.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import org.junit.jupiter.api.Test;

public class DefaultColorThemeTest {
    @Test
    public void sanityDefaultColorThemeTest() {
    ColorTheme colors = new DefaultColorTheme();
    assertEquals(Color.BLACK, colors.getBackgroundColor());
    assertEquals(Color.darkGray, colors.getFrameColor());
    assertEquals(Color.GRAY, colors.getCellColor('-'));
    assertEquals(Color.MAGENTA, colors.getCellColor('r'));
    assertThrows(IllegalArgumentException.class, () -> colors.getCellColor('\n'));
    }
}
