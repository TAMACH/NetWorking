/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package td;

/**
 *
 * @author Maroine
 */
public interface MySerialisable {

    public void writeToBuff(SerializerBuffer ms);

    public void readFromBuff(SerializerBuffer ms);

}
