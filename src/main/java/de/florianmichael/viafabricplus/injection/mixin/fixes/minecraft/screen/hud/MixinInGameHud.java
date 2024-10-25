/*
 * This file is part of ViaFabricPlus - https://github.com/FlorianMichael/ViaFabricPlus
 * Copyright (C) 2021-2024 FlorianMichael/EnZaXD <florian.michael07@gmail.com> and RK_01/RaphiMC
 * Copyright (C) 2023-2024 contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.florianmichael.viafabricplus.injection.mixin.fixes.minecraft.screen.hud;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import de.florianmichael.viafabricplus.settings.impl.VisualSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {

    @Unique
    private static final int viaFabricPlus$ARMOR_ICON_WIDTH = 8;

    @Inject(method = {"renderMountJumpBar", "renderMountHealth"}, at = @At("HEAD"), cancellable = true)
    private void removeMountJumpBar(CallbackInfo ci) {
        if (VisualSettings.global().hideModernHUDElements.isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "getHeartCount", at = @At("HEAD"), cancellable = true)
    private void removeHungerBar(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        if (VisualSettings.global().hideModernHUDElements.isEnabled()) {
            cir.setReturnValue(1);
        }
    }

    @ModifyExpressionValue(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;getScaledWindowHeight()I"), require = 0)
    private int moveHealthDown(int value) {
        if (VisualSettings.global().hideModernHUDElements.isEnabled()) {
            return value + 6; // Magical offset
        } else {
            return value;
        }
    }

    // TODO UPDATE-1.21.3
//    @ModifyArgs(method = "renderArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"), require = 0)
//    private static void moveArmorPositions(Args args, @Local(ordinal = 3, argsOnly = true) int x, @Local(ordinal = 6) int n) {
//        if (!VisualSettings.global().hideModernHUDElements.isEnabled()) {
//            return;
//        }
//        final MinecraftClient client = MinecraftClient.getInstance();
//
//        final int armorWidth = 10 * viaFabricPlus$ARMOR_ICON_WIDTH;
//        final int offset = n * viaFabricPlus$ARMOR_ICON_WIDTH;
//
//        args.set(1, client.getWindow().getScaledWidth() - x - armorWidth + offset - 1);
//        args.set(2, (int) args.get(2) + client.textRenderer.fontHeight + 1);
//    }
//
//    @ModifyArg(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"), slice = @Slice(
//            from = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 2),
//            to = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;pop()V")), index = 1, require = 0)
//    private int moveAir(int value) {
//        if (VisualSettings.global().hideModernHUDElements.isEnabled()) {
//            final MinecraftClient client = MinecraftClient.getInstance();
//            return client.getWindow().getScaledWidth() - value - client.textRenderer.fontHeight;
//        } else {
//            return value;
//        }
//    }

}