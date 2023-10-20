package com.example.geocaching

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geocaching.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db:SqlliteDBhelper
    private lateinit var courseAdapter: courseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db= SqlliteDBhelper(this)
        var recyclerview: RecyclerView =findViewById(R.id.recyclerview)
        courseAdapter= courseAdapter(db.getNotes(),this)
        var gridlayout= GridLayoutManager(this,2)
        recyclerview.layoutManager=gridlayout
        recyclerview.adapter=courseAdapter

        binding.Addcourse.setOnClickListener(){
                addCourse()
        }

    }

    private fun addCourse(){
        val inflater=LayoutInflater.from(this)
        val v=inflater.inflate(R.layout.addcourseitem,null)
        val addDailog=AlertDialog.Builder(this,R.style.MyDialogTheme)
        db= SqlliteDBhelper(this)
        var coursename=v.findViewById<EditText>(R.id.coursename)
        addDailog.setView(v)
        addDailog.setPositiveButton("Add"){dailog,_->
            val course=coursename.text.toString().trim()
            if(course.isNotEmpty()){
                var note=ItemCourse(0,course)
                db.addNote(note)
                courseAdapter.notifyDataSetChanged()
                dailog.dismiss()


            }

        }
        addDailog.setNegativeButton("Cancel"){dailog,_->
            dailog.dismiss()
            Toast.makeText(this,"Canceld",Toast.LENGTH_SHORT).show()

        }
        addDailog.create()
        addDailog.show()

    }

    override fun onResume() {
        super.onResume()
        courseAdapter.refresh(db.getNotes())
    }

}