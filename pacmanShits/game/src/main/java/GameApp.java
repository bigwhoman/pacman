import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.asset.*;
public class GameApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("breakout");
        gameSettings.setVersion("dev");
        gameSettings.setWidth(640);
        gameSettings.setHeight(960);
        gameSettings.setIntroEnabled(true);
        gameSettings.setMenuEnabled(true);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
