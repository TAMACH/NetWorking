/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package td2;

/**
 *
 * @author Maroine
 */
public interface Creator<T extends MySerialisable> {

    public T init();
}
