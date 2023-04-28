package com.comunidadedevspace.taskbeats


import android.app.Activity
import android.app.Instrumentation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var taskList = arrayListOf(
        Task(0,"Empilhador Toyota", "Empilhador do Joao"),
        Task(1,"Komatsu", "Giratoria do manuel"),
        Task(2,"Senneboghen", "Giratoria do jose"),
        Task(3,"Empilhador Eletrico", "Empilhador do Wang"),
        Task(4,"Empilhador Manitou", "Empilhador do Nuno"),
        Task(5,"Senneboghen", "Giratoria do Ricardo"),
        Task(6,"Maquina do Baldo", "Maquina do Sergio"),
        Task(7,"Prensa", "Prensa do Agny"),
        Task(8,"Balanca pequena", "Balanca de dentro , jean"),
        Task(9,"Balanca de fora", "Balanca Principal"),

    )

    private lateinit var ctn_content: LinearLayout
    private val adapter: TaskListAdapter = TaskListAdapter(::openTaskDetailView)

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // faça algo aqui
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task

            val newList = arrayListOf<Task>()
                .apply {
                    addAll(taskList)
                }



            taskList.remove(task)

            if (taskList.size == 0){
                ctn_content.visibility = View.VISIBLE
            }



            adapter.submitList(newList)

            taskList = newList

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        ctn_content = findViewById(R.id.ctn_content)
        adapter.submitList(taskList)


        val rvTask: RecyclerView = findViewById(R.id.rv_task_list)
        rvTask.adapter = adapter
    }

    private fun openTaskDetailView(task: Task) {
        val intent = TaskDetailActivity.start(this, task)




        startForResult.launch(intent)


    }
}


sealed class ActionType : Serializable {
    object DELETE : ActionType()
    object UPTADE : ActionType()
    object CREATE : ActionType()

}

data class TaskAction(val task: Task,
                      val actionType: ActionType) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"