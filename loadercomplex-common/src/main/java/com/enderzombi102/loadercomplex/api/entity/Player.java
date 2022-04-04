package com.enderzombi102.loadercomplex.api.entity;

import com.enderzombi102.loadercomplex.api.utils.Gamemode;
import com.enderzombi102.loadercomplex.api.utils.Position;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.AvailableSince("0.1.3")
public interface Player extends LivingEntity {
	boolean isSleeping();
	Position getBedLocation();
	int getScore();
	void addScore(int score);
	void sendMessage(String msg);
	void setRespawnPoint(Position pos);

	int getFoodLevel();
	void setFoodLevel(int level);
	float getSaturationLevel();
	void setSaturationLevel(float level);

	Gamemode getGamemode();

	int getExperienceLevel();
}
