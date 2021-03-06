/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Psi Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: http://psi.vazkii.us/license.php
 *
 * File Created @ [23/01/2016, 15:48:38 (GMT)]
 */
package vazkii.psi.api.spell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.util.text.translation.I18n;
import vazkii.psi.api.internal.IPlayerData;

/**
 * Base class for a "Piece Group", used for the leveling system.
 */
public class PieceGroup implements Comparable<PieceGroup> {

	public final String name;
	public List<Class<? extends SpellPiece>> pieces = new ArrayList();
	public Class<? extends SpellPiece> mainPiece = null;
	public List<String> requirements = new ArrayList();
	public int levelRequirement = 0;

	public PieceGroup(String name) {
		this.name = name;
	}

	public void addPiece(Class<? extends SpellPiece> piece, boolean main) {
		pieces.add(piece);
		if(main)
			mainPiece = piece;
	}

	public void setRequirements(int level, String... reqs) {
		levelRequirement = level;
		requirements = Arrays.asList(reqs);
	}

	public boolean isAvailable(IPlayerData data) {
		if(data.getLevel() < levelRequirement)
			return false;

		for(String s : requirements)
			if(!data.isPieceGroupUnlocked(s))
				return false;
		return true;
	}

	public String getUnlocalizedName() {
		return "psi.piecegroup." + name;
	}

	public String getUnlocalizedDesc() {
		return getUnlocalizedName() + ".desc";
	}

	@Override
	public int compareTo(PieceGroup o) {
		if(o.levelRequirement == levelRequirement)
			return I18n.translateToLocal(getUnlocalizedName()).compareTo(I18n.translateToLocal(o.getUnlocalizedName()));

		return levelRequirement - o.levelRequirement;
	}

}
