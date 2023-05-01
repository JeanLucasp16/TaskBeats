package com.comunidadedevspace.taskbeats


import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var taskList = arrayListOf(
        Task(0, "Empilhador Toyota", "S/N 123456789"),
        Task(1, "Komatsu Giratoria", "S/N 123456789 "),
        Task(2, "Senneboghen Giratoria", "S/N 123456789"),
        Task(3, "Empilhador Eletrico", "S/N 123456789"),
        Task(4, "Empilhador Manitou", "S/N 123456789"),
        Task(5, "Senneboghen Giratoria", "S/N 123456789"),
        Task(6, "Maquina do Baldo", "S/N 123456789"),
        Task(7, "Prensa", "S/N 123456789"),
        Task(8, "Balanca pequena", "S/N 123456789"),
        Task(9, "Balanca de fora", "S/N 123456789"),

        )

    private lateinit var ctn_content: LinearLayout
    private val adapter: TaskListAdapter = TaskListAdapter(::onListItemClicked)

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // faça algo aqui
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task


            if (taskAction.actionType == ActionType.DELETE.name){
                val newList = arrayListOf<Task>()
                    .apply {
                        addAll(taskList)
                    }

                newList.remove(task)

                showMessage(ctn_content,"Item Deleted ${task.title}")

                if (newList.size == 0) {
                    ctn_content.visibility = View.VISIBLE
                }



                adapter.submitList(newList)

                taskList = newList

            }else if (taskAction.actionType == ActionType.CREATE.name){
                val newList = arrayListOf<Task>()
                    .apply {
                        addAll(taskList)
                    }
                newList.add(task)

                showMessage(ctn_content,"Item added ${task.title}")

                adapter.submitList(newList)
                taskList = newList
            }else if (taskAction.actionType == ActionType.UPTADE.name){


                val tempEmptyList = arrayListOf<Task>()
                taskList.forEach {
                    if (it.id ==task.id){
                        val newItem = Task(it.id,
                            task.title,
                            task.description)

                        tempEmptyList.add(
                            newItem
                        )

                    }else {
                        tempEmptyList.add(it)
                    }
                }

                showMessage(ctn_content,"Item updated ${task.title}")
                adapter.submitList(tempEmptyList)
                taskList = tempEmptyList

            }



        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        ctn_content = findViewById(R.id.ctn_content)
        adapter.submitList(taskList)


        val rvTask: RecyclerView = findViewById(R.id.rv_task_list)
       rvTask.adapter = adapter


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            openTaskListDetail(null)

        }

    }
    private fun showMessage(view: View,message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }



    private fun onListItemClicked(task: Task) {
       openTaskListDetail(task)
    }

    private fun openTaskListDetail(task: Task? = null){
        val intent = TaskDetailActivity.start(this, task)
        startForResult.launch(intent)

    }
}


enum class  ActionType   {
     DELETE,
     UPTADE ,
     CREATE ,

}

data class TaskAction(val task: Task,
                      val actionType: String
                      ) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"