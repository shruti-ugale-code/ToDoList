import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

class Task extends JPanel {

    JLabel index;
    JTextField taskName;
    JButton done;

    Color  Text_bgColor= new Color(226, 191, 217);//BACKGROUND FOR TEXTFEILD
    Color taskdone = new Color(23, 155, 174);
    Color doneColor = new Color(103, 65, 136);//BACKGROUND OF DONE

    private boolean checked;

    Task() {
        this.setPreferredSize(new Dimension(400, 20)); // set size of task
        this.setBackground(Text_bgColor); // set background color of task

        this.setLayout(new BorderLayout()); // set layout of task

        checked = false;

        index = new JLabel(""); // create index no to add tasks using changeIndex() function
        index.setPreferredSize(new Dimension(50, 50)); // set size of index label
        index.setHorizontalAlignment(JLabel.CENTER); // set alignment of index label
        this.add(index, BorderLayout.WEST); // add index label to task

        taskName = new JTextField("WRITE YOUR TASK HERE.."); // create task name text field
        taskName.setBorder(BorderFactory.createEmptyBorder()); // remove border of text field
        taskName.setBackground(Text_bgColor); // set background color of text field
        taskName.setFont(new Font("Serif", Font.BOLD, 14));

        this.add(taskName, BorderLayout.CENTER);

        done = new JButton("DONE");
        done.setPreferredSize(new Dimension(80, 40));
        done.setBorder(BorderFactory.createEmptyBorder());
        done.setBackground(doneColor);//background of done button
        done.setForeground(Color.WHITE);//Textcolor
        done.setFocusPainted(false);

        this.add(done, BorderLayout.EAST);

    }

    public void changeIndex(int num) {
        this.index.setText(num + ""); // num to String
        this.revalidate(); // refresh
    }

    public JButton getDone() {
        return done;
    }

    public boolean getState() {
        return checked;
    }

    public void changeState() {
        this.setBackground(taskdone);
        taskName.setBackground(taskdone);
        checked = true;
        revalidate();
    }
}

class List extends JPanel {

    Color lightColor = new Color(252, 221, 176);

    List() {

        GridLayout layout = new GridLayout(10, 1);
        layout.setVgap(5); // Vertical gap

        this.setLayout(layout); // After 10 tasks, no of columns can be extended
        this.setPreferredSize(new Dimension(400, 560));
        this.setBackground(lightColor);
    }

    //incrementing the numbers of index
    public void updateNumbers() {
        Component[] listItems = this.getComponents();

        for (int i = 0; i < listItems.length; i++) {
            if (listItems[i] instanceof Task) {
                ((Task) listItems[i]).changeIndex(i + 1);
            }
        }

    }

    public void removeCompletedTasks() {

        for (Component c : getComponents()) {
            if (c instanceof Task) {
                if (((Task) c).getState()) {
                    remove(c); // remove the component
                    updateNumbers(); // update the indexing of all items
                }
            }
        }

    }
}

class Footer extends JPanel {

    JButton addTask;
    JButton clear;

    Color btn_color = new Color(103, 65, 136);//COLOR FOR BUTTONS
    Color lightColor = new Color(187, 233, 255);//background color below 2 buttons
    Border emptyBorder = BorderFactory.createLineBorder(Color.black);

    Footer() {
        this.setPreferredSize(new Dimension(400,60));
        this.setBackground(lightColor);

        addTask = new JButton("Add Task"); // add task button
        addTask.setBorder(emptyBorder); // remove border
        addTask.setFont(new Font("Sans-serif", Font.ITALIC, 20)); // set font
        addTask.setForeground(Color.WHITE);
        addTask.setVerticalAlignment(JButton.BOTTOM); // align text to bottom
        addTask.setBackground(btn_color); // set background color
        addTask.setPreferredSize(new Dimension(120, 40));
        this.add(addTask); // add to footer


        this.add(Box.createHorizontalStrut(20)); // Space between buttons

        clear = new JButton("Clear finished tasks"); // clear button
        clear.setFont(new Font("Sans-serif", Font.ITALIC, 20)); // set font
        clear.setForeground(Color.WHITE);
        clear.setBorder(emptyBorder); // remove border
        clear.setBackground(btn_color); // set background color
        clear.setPreferredSize(new Dimension(200, 40));
        this.add(clear); // add to footer
    }

    public JButton getNewTask() {
        return addTask;
    }

    public JButton getClear() {
        return clear;
    }
}

class TitleBar extends JPanel {

    Color lightColor = new Color(187, 233, 255);

    //For title of project
    TitleBar() {
        this.setPreferredSize(new Dimension(400, 80)); // Size of the title bar
        this.setBackground(lightColor); // Color of the title bar
        JLabel titleText = new JLabel("TO-DO-LIST ");
        JLabel line = new JLabel();// Text of the title bar
        titleText.setPreferredSize(new Dimension(200, 60)); // Size of the text
        titleText.setFont(new Font("TimesRoman", Font.BOLD, 28)); // Font of the text
        titleText.setHorizontalAlignment(JLabel.CENTER); // Align the text to the center
        this.add(titleText); // Add the text to the title bar
        this.add(line);
    }
}

class AppFrame extends JFrame {

    private TitleBar title;
    private Footer footer;
    private List list;

    private JButton newTask;
    private JButton clear;

    AppFrame() {
        this.setSize(500, 700); // width and height
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit
        this.setVisible(true); // Make visible

        title = new TitleBar();
        footer = new Footer();
        list = new List();

        this.add(title, BorderLayout.NORTH); // Add title bar on top of the screen
        this.add(footer, BorderLayout.SOUTH); // Add footer on bottom of the screen
        this.add(list, BorderLayout.CENTER); // Add list in middle of footer and title

        newTask = footer.getNewTask();
        clear = footer.getClear();

        addListeners();
    }

    public void addListeners() {
        newTask.addMouseListener(new MouseAdapter() {
            @override
            public void mousePressed(MouseEvent e) {
                Task task = new Task();
                list.add(task); // Add new task to list
                list.updateNumbers(); // Updates the numbers of the tasks

                task.getDone().addMouseListener(new MouseAdapter() {
                    @override
                    public void mousePressed(MouseEvent e) {

                        task.changeState(); // Change color of task
                        list.updateNumbers(); // Updates the numbers of the tasks
                        revalidate(); // Updates the frame

                    }
                });
            }

        });
        clear.addMouseListener(new MouseAdapter() {
            @override
            public void mousePressed(MouseEvent e) {
                list.removeCompletedTasks(); // Removes all tasks that are done
                repaint(); // Repaints the list
            }
        });
    }

}

public class ToDoList {
    public static void main(String args[]) {
        AppFrame frame = new AppFrame(); // Create the frame
    }
}
@interface override {

}