/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2017.ex4;

import java.util.Collection;

/**
 *
 * @author tamac
 */
public interface SimpleCalandar {

    Event getEvent(int id);

    Collection<Event> getEventsByDates(int date);

    Collection<Event> getAllEvents();

    void addEvent(Event e);

    void deleteEvent(int id);

}
