package cn.ksmcbrigade.rd_mh;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.ModInitializer;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.IOException;

public class MovementHacksMod implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger(MovementHacksMod.class.getSimpleName());

	public static File configFile = new File("config/mh-config.json");

	public static int sprintKey = Keyboard.KEY_CAPITAL;
	public static int flyKey = Keyboard.KEY_G;
	public static int shiftKey = Keyboard.KEY_LSHIFT;

	public static float sprintMulti = 2.85f;
	public static float flyDown = 0.15f,flyUp = 0.15f;

	public static boolean flyNoGravity = true;

	public static boolean sprintEnable = false;
	public static boolean flyEnable = false;

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public void onInitialize() {
		try {
			if(!configFile.exists()){
				save();
				return;
			}
			JsonObject object = JsonParser.parseString(FileUtils.readFileToString(configFile)).getAsJsonObject();
			if(object.has("sprint-key")) sprintKey = object.get("sprint-key").getAsInt();
			if(object.has("shift-key")) sprintKey = object.get("shift-key").getAsInt();
			if(object.has("fly-key")) sprintKey = object.get("fly-key").getAsInt();

			if(object.has("sprint-multi")) sprintMulti = object.get("sprint-multi").getAsFloat();

			if(object.has("fly-down")) flyDown = object.get("fly-down").getAsFloat();
			if(object.has("fly-up")) flyUp = object.get("fly-up").getAsFloat();

			if(object.has("fly-no-gravity")) flyNoGravity = object.get("fly-no-gravity").getAsBoolean();
			save();

		} catch (Throwable e) {
			LOGGER.error("Failed to load configs.",e);
		}
	}

	public static void save() throws IOException {
		JsonObject object = new JsonObject();
		object.addProperty("sprint-key",sprintKey);
		object.addProperty("shift-key",shiftKey);
		object.addProperty("fly-key",flyKey);

		object.addProperty("sprint-multi",sprintMulti);

		object.addProperty("fly-down",flyDown);
		object.addProperty("fly-up",flyUp);

		object.addProperty("fly-no-gravity",flyNoGravity);
		FileUtils.writeStringToFile(configFile,GSON.toJson(object));
	}
}
