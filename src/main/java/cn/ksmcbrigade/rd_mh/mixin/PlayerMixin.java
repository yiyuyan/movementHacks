package cn.ksmcbrigade.rd_mh.mixin;

import cn.ksmcbrigade.rd_mh.MovementHacksMod;
import com.mojang.rubydung.Player;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class,remap = false)
public class PlayerMixin {
	@Shadow
	public float yd;

	@Shadow
	public boolean onGround;

	@ModifyVariable(method = "moveRelative", at = @At("HEAD"), ordinal = 2, argsOnly = true)
	public float modifySpeed(float value){
		return value * (MovementHacksMod.sprintEnable?MovementHacksMod.sprintMulti:1.0f);
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/mojang/rubydung/Player;move(FFF)V", shift = At.Shift.BEFORE))
	public void moved(CallbackInfo ci) {
		if(MovementHacksMod.flyEnable && MovementHacksMod.flyNoGravity){
			this.yd += 0.005F;
			this.onGround = false;
		}

		if (MovementHacksMod.flyEnable) {
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				this.yd = MovementHacksMod.flyUp;
			} else if (Keyboard.isKeyDown(MovementHacksMod.shiftKey)) {
				this.yd = -MovementHacksMod.flyDown;
			} else {
				this.yd = 0.0F;
			}
		}
	}

	@ModifyConstant(method = "tick",constant = @Constant(floatValue = 0.98f))
	public float noGravity0(float constant){
		if(MovementHacksMod.flyEnable && MovementHacksMod.flyNoGravity){
			return 1f;
		}
		return constant;
	}

}
