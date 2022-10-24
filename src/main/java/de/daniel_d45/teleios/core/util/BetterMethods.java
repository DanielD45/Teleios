/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core.util;

public class BetterMethods {

    public static boolean betterEquals(Object obj1, Object obj2) {
        try {

            if (obj1 == null && obj2 == null) {
                return true;
            }

            return obj1.equals(obj2);

        } catch (NullPointerException e) {
            // One of the objects is null
            return false;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("BetterMethods", "betterEquals(" + obj1 + ", " + obj2 + ")", e);
            return false;
        }

    }

}
