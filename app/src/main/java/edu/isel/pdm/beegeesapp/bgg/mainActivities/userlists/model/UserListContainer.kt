package edu.isel.pdm.beegeesapp.bgg.mainActivities.userlists.model

import android.os.Parcelable
import edu.isel.pdm.beegeesapp.bgg.databaseUtils.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import kotlinx.android.parcel.Parcelize


@Parcelize
class UserListContainer : Parcelable {

       var userLists : MutableList<CustomUserList> = mutableListOf()

     fun addList(name : String) {
     val newList = CustomUserList(name)
     userLists.add(newList)
}


     fun addList(list : CustomUserList){
          userLists.add(list)
     }

     fun getAllListsNames() : MutableList<String>{
          val list = mutableListOf<String>()
          userLists.forEach {
               list.add(it.listName)
          }
          return list
     }

     fun addGameToList(listName : String, game : GameInfo) : Boolean{
          val currentList : CustomUserList? = userLists.find { it.listName == listName }
          if ( currentList!= null){ //EXISTE UMA LISTA COM O NOME DO PARAMETRO
               currentList.gamesList.add(game)
               return true
          }
          return false
     }

     fun addListAt(userList: CustomUserList, removedPosition: Int) {
          userLists.add(removedPosition,userList)
     }


}