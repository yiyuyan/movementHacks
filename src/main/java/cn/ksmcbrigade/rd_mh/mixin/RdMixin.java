package cn.ksmcbrigade.rd_mh.mixin;

import cn.ksmcbrigade.rd_mh.MovementHacksMod;
import com.mojang.rubydung.RubyDung;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RubyDung.class,remap = false)
public class RdMixin {

	@Unique
	private static boolean prevFlyKeyDown = false;

	@Unique
	private static boolean prevSprintKeyDown = false;

	@Inject(method = "tick",at = @At("HEAD"))
	public void tick(CallbackInfo ci){
		boolean nowFly = Keyboard.isKeyDown(MovementHacksMod.flyKey);
		if (nowFly && !prevFlyKeyDown) {
			MovementHacksMod.flyEnable = !MovementHacksMod.flyEnable;
		}
		prevFlyKeyDown = nowFly;

		boolean nowSprint = Keyboard.isKeyDown(MovementHacksMod.sprintKey);
		if (nowSprint && !prevSprintKeyDown) {
			MovementHacksMod.sprintEnable = !MovementHacksMod.sprintEnable;
		}
		prevSprintKeyDown = nowSprint;
	}
}
