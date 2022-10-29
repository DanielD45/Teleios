/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core.program;


/**
 * This class contains java's main method.
 *
 * @author Daniel_D45
 */
public class Boot {

    public static void main(String[] args) {
        try {

            // Program test room

            // End of program test room
            MessageMaster.sendSuccessMessage("Boot", "main()");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("Boot", "main()", e);
        }

    }

}
