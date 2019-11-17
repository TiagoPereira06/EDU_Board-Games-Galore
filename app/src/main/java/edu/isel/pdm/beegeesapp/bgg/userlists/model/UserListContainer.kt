package edu.isel.pdm.beegeesapp.bgg.userlists.model

import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo

class UserListContainer {

      var userLists : MutableList<CustomUserList> = mutableListOf()

     fun addList(name : String) {
     val newList = CustomUserList(name)
     userLists.add(newList)
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
               currentList.list.add(game)
               return true
          }
          return false
     }


}