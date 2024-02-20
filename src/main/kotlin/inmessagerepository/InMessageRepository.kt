package inmessagerepository


object InMessageRepository {

    /**
     * Хранилище id входящих сообщений.
     * <id сообщения, время получения сообщения>
     */
    private val inMessageMap = mutableMapOf<String, Long>()

    /**
     *  Максимально допустимое количество сообщений, данные о которых мы храним.
     */
    private const val maxInMessageMap = 1024

    /**
     * Проверялось ли уже данное сообщение.
     *  - Если данный метод вызвать несколько раз подряд, первый раз вернет false, все остальные true.
     *  - При заполнении хранилища информация о старых сообщениях удаляется,
     * @param id сообщения
     * @return true - проверялось, false - не проверялось.
     */
    fun whetherTheMessageHasAlreadyBeenChecked(id : String) : Boolean
    {
        return whetherTheMessageHasAlreadyBeenReceived(id).also {
            if(!it)
            {
                cleanInMessageMap()
                inMessageMap[id] = System.currentTimeMillis()
            }
        }
    }


    private fun whetherTheMessageHasAlreadyBeenReceived(id : String) : Boolean
    {
        return inMessageMap.containsKey(id)
    }

    private fun cleanInMessageMap()
    {
        while(inMessageMap.size>= maxInMessageMap)
        {
            val minValue = inMessageMap.map { it.value }.min()
            inMessageMap.entries.removeIf { it.value==minValue }
        }
    }

}