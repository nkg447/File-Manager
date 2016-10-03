import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;


public class CFrame implements WindowListener
{
	Runtime rt = Runtime.getRuntime();
	String path;
	JFrame frame=new JFrame();
	JButton button[];
	public CFrame(String loc) {
		// TODO Auto-generated constructor stub
		path=loc;
		frame.setName(path);
		frame.setLayout(null);
		frame.setSize(1500, 700);
		frame.setName(path);
		File flist[]=(new File(path)).listFiles();
		button=new JButton[flist.length];
		createMenuBar();
		createFrame(new File(path),3);
		frame.setVisible(true);
	}
	
	
	public CFrame() {
		// TODO Auto-generated constructor stub
		path="";
		frame.setName("This PC");
		frame.setLayout(null);
		frame.setSize(1500, 700);
		createMenuBar();
		
		createMainFrame();
		frame.setVisible(true);
	}
	
	
	private void createMenuBar() 
	{
		// TODO Auto-generated method stub
		JMenuBar menubar=new JMenuBar();
		menubar.setBounds(0, 0, 1500, 30);
		JMenu file=new JMenu("File");
		menubar.add(file);
		JMenu view=new JMenu("View");
		menubar.add(view);
		
		JMenuItem openInNew=new JMenuItem("Open in new Frame");
		file.add(openInNew);
		openInNew.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0) 
			{
				// TODO Auto-generated method stub
				new CFrame(path);
			}
		
		});
		
		JMenuItem cmd=new JMenuItem("Open cmd here");
		file.add(cmd);
		cmd.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0) 
			{
				// TODO Auto-generated method stub
				try {
					rt.exec("cmd.exe /c start", null, (new File(path)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		});
		
		JMenuItem close=new JMenuItem("close");
		file.add(close);
		close.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0) 
			{
				// TODO Auto-generated method stub
				System.exit(0);
			}
		
		});
		JMenu sortBy=new JMenu("Sort By");
		JMenuItem bydate=new JMenuItem("date");
		JMenuItem byname=new JMenuItem("name");
		JMenuItem bysize=new JMenuItem("size");
		sortBy.add(byname);
		sortBy.add(bydate);
		sortBy.add(bysize);
		byname.addActionListener(defaultHandler);
		bydate.addActionListener(defaultHandler);
		bysize.addActionListener(defaultHandler);
		view.add(sortBy);
		
		
		frame.add(menubar);
	}


	private void createMainFrame() {
		// TODO Auto-generated method stub
		final File flist[]=File.listRoots();
		button=new JButton[flist.length];
		
		
		for(int i=0;i<flist.length;i++)
		{
			button[i]=new JButton();
			button[i].setLabel(flist[i].getPath());
			
			button[i].setBounds(30,(i+2)*40, 100, 30);
			final int indx = i;
			button[i].addActionListener(new ActionListener()
			{

				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					removeComponent(frame);
					createFrame(flist[indx],3);
				}
				private void removeComponent(JFrame frame) {
					// TODO Auto-generated method stub
					for(int indx=0;indx<flist.length;indx++)
					{
						frame.remove(button[indx]);
					}
				}
				
			});
			frame.add(button[i]);
		}
		frame.addWindowListener(this);
		SwingUtilities.updateComponentTreeUI(frame);
	}


	void createFrame(File dir,int mode) 
	{
		final File newList[]=dir.listFiles();
		(new SortBy()).sort(newList,mode);
		addBackButton();
		path=newList[0].getParent();
		System.out.println(path);
		button=new JButton[newList.length];
		for(int i=0;i<newList.length;i++)
		{
			final int indx = i;
			button[i]=new JButton();
			button[i].setLabel(newList[i].getName());
			button[i].setBounds((i%8)*150+30,((i/8)+2)*40, 150, 30);
			if(newList[i].isFile())
			{
				button[i].setBackground(new java.awt.Color(0,204,204));
				button[i].addActionListener(new ActionListener()
				{

					public void actionPerformed(ActionEvent arg0) {
						
						try {
							open(newList[indx].getPath());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				});
			}
			else
			{
				button[i].addActionListener(new ActionListener()
				{

					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						System.out.println(path);
						
						removeComponent(path);
						createFrame(newList[indx],3);
					}
					
				});
			}
			
			
			
			frame.add(button[i]);
			
		}
		SwingUtilities.updateComponentTreeUI(frame);	
			
	}


	private void addBackButton() {
		final JButton back=new JButton("BACK");
		back.setBounds(30, 40, 80, 30);
		
		back.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				if(path.length()<4)
				{
					removeComponent(path);
					frame.remove(back);
					createMainFrame();
				}
				else
				{
					
					File parentDir=new File(path);
					parentDir=new File(parentDir.getParent());
					removeComponent(path);
					createFrame(parentDir,3);
				}
				
			}
			
		});
		frame.add(back);
		SwingUtilities.updateComponentTreeUI(frame);	
	}


	void removeComponent(String parent) {
		// TODO Auto-generated method stub
		File temp=new File(parent);
		//temp=new File(temp.getParent());
		File templ[]=temp.listFiles();
		for(int i=0;i<templ.length;i++)
			frame.remove(button[i]);
		
	}


	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		System.exit(0);
	}


	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		System.exit(0);
	}


	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public static void open(String targetFilePath) throws IOException
	{
		Desktop desktop = Desktop.getDesktop();

		desktop.open(new File(targetFilePath));
	}
	
	ActionListener defaultHandler = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	      if(e.getActionCommand().equals("name"))
	      {
	    	  removeComponent(path);
	    	  createFrame(new File(path),3);
	      }
	      else if(e.getActionCommand().equals("date"))
	      {
	    	  removeComponent(path);
	    	  createFrame(new File(path),1);
	      }
	      else if(e.getActionCommand().equals("size"))
	      {
	    	  removeComponent(path);
	    	  createFrame(new File(path),2);
	      }
	     
	    }
	  };
}
