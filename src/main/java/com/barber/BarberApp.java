package com.barber;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import javax.imageio.ImageIO;

/** BARBER phone-sized Swing application. No third-party dependencies. */
public final class BarberApp {
    public static final Color PURPLE=new Color(108,66,207), INK=new Color(35,27,51), MUTED=new Color(122,112,136), MINT=new Color(221,239,227);
    static Font font(int size,int style){return new Font("SansSerif",style,size);}
    static JLabel label(String text,int size,int style,Color color){JLabel l=new JLabel(text);l.setFont(font(size,style));l.setForeground(color);l.setAlignmentX(Component.LEFT_ALIGNMENT);return l;}
    static void gap(JPanel p,int n){p.add(Box.createVerticalStrut(n));}
    static void item(JPanel p,JComponent c){c.setAlignmentX(Component.LEFT_ALIGNMENT);p.add(c);}

    public static final class Screen extends JPanel {
        final CardLayout cards=new CardLayout();final JPanel body=new JPanel(cards);
        final LoginPanel login;final RegisterPanel register;
        Screen(){
            setPreferredSize(new Dimension(420,860));setLayout(new BorderLayout(0,0));setBorder(new EmptyBorder(0,22,22,22));
            add(new Header(),BorderLayout.NORTH);body.setOpaque(false);login=new LoginPanel(this);register=new RegisterPanel(this);body.add(login,"login");body.add(register,"register");add(body,BorderLayout.CENTER);
        }
        void page(String name){cards.show(body,name);JRootPane root=SwingUtilities.getRootPane(this);if(root!=null)root.setDefaultButton(name.equals("login")?login.submit:register.submit);}
        protected void paintComponent(Graphics g){Graphics2D p=(Graphics2D)g.create();p.setPaint(new GradientPaint(0,0,new Color(50,30,90),420,480,new Color(94,56,158)));p.fillRect(0,0,getWidth(),getHeight());p.setColor(new Color(246,243,251));p.fillRect(0,390,getWidth(),getHeight());p.dispose();}
    }
    static final class Header extends JComponent {
        final BufferedImage logo;
        Header(){setPreferredSize(new Dimension(376,264));try(InputStream in=BarberApp.class.getResourceAsStream("/images/barber-logo.png")){if(in==null)throw new IOException("Logo bulunamadı");logo=ImageIO.read(in);}catch(IOException ex){throw new IllegalStateException(ex);}}
        protected void paintComponent(Graphics g){Graphics2D p=(Graphics2D)g.create();p.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            p.setColor(new Color(255,255,255,18));p.setStroke(new BasicStroke(1));for(int i=0;i<4;i++)p.drawOval(246+i*19,49-i*12,170,170);
            p.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            // Only the original B emblem region; no redraw, recolor or distortion.
            Shape clip=p.getClip();p.clip(new java.awt.geom.RoundRectangle2D.Double(3,32,56,58,17,17));p.drawImage(logo,3,32,59,90,25,24,81,82,null);p.setClip(clip);
            p.setColor(Color.WHITE);p.setFont(font(22,Font.BOLD));p.drawString("BARBER",73,68);
            p.setColor(new Color(206,188,233));p.setFont(font(10,Font.PLAIN));p.drawString("BAKIMIN İÇİN BİR YER AYIR.",74,86);
            p.setColor(Color.WHITE);p.setFont(font(31,Font.BOLD));p.drawString("Berberin cebinde.",3,145);
            p.setColor(new Color(210,237,219));p.drawString("Zamanını yönet.",3,184);
            p.setColor(new Color(211,197,232));p.setFont(font(12,Font.PLAIN));p.drawString("Bir sonraki iyi görünümüne giriş yap.",4,217);
            p.setColor(new Color(210,237,219));p.fillRoundRect(4,238,29,3,3,3);p.setColor(new Color(255,255,255,45));p.fillRoundRect(39,238,9,3,3,3);p.dispose();
        }
    }
    static class Card extends JPanel {
        Card(){setOpaque(false);setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));setBorder(new EmptyBorder(27,24,23,24));}
        protected void paintComponent(Graphics g){Graphics2D p=(Graphics2D)g.create();p.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);p.setColor(new Color(54,28,88,12));p.fillRoundRect(0,5,getWidth(),getHeight()-5,30,30);p.setColor(Color.WHITE);p.fillRoundRect(0,0,getWidth(),getHeight()-5,30,30);p.dispose();super.paintComponent(g);}
    }
    static final class Entry extends JPanel {
        final JTextField field;
        Entry(String name,boolean password){setLayout(new BorderLayout(10,0));setOpaque(false);setBorder(new EmptyBorder(0,14,0,8));setMaximumSize(new Dimension(Integer.MAX_VALUE,52));setPreferredSize(new Dimension(328,52));
            JLabel icon=label(password?"•":name.equals("Ad soyad")?"○":"@",password?24:18,Font.BOLD,new Color(158,142,183));add(icon,BorderLayout.WEST);
            field=password?new JPasswordField():new JTextField();field.setOpaque(false);field.setBorder(new EmptyBorder(0,0,0,0));field.setForeground(INK);field.setCaretColor(PURPLE);field.setFont(font(14,Font.PLAIN));field.getAccessibleContext().setAccessibleName(name);add(field);
            if(password){JPasswordField pf=(JPasswordField)field;pf.setEchoChar('●');JToggleButton show=new JToggleButton("Göster");show.setFont(font(10,Font.BOLD));show.setForeground(PURPLE);show.setBorder(new EmptyBorder(8,3,8,3));show.setContentAreaFilled(false);show.addActionListener(e->{pf.setEchoChar(show.isSelected()?(char)0:'●');show.setText(show.isSelected()?"Gizle":"Göster");});add(show,BorderLayout.EAST);}
            field.addFocusListener(new FocusAdapter(){public void focusGained(FocusEvent e){repaint();}public void focusLost(FocusEvent e){repaint();}});
        }
        protected void paintComponent(Graphics g){Graphics2D p=(Graphics2D)g.create();p.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);p.setColor(new Color(248,246,251));p.fillRoundRect(0,0,getWidth(),getHeight(),14,14);p.setColor(field.hasFocus()?PURPLE:new Color(232,226,240));p.setStroke(new BasicStroke(field.hasFocus()?2:1));p.drawRoundRect(1,1,getWidth()-3,getHeight()-3,14,14);p.dispose();super.paintComponent(g);}
    }
    static final class ActionButton extends JButton {
        final boolean secondary;
        ActionButton(String text,boolean secondary){super(text);this.secondary=secondary;setFont(font(14,Font.BOLD));setForeground(secondary?new Color(47,77,61):Color.WHITE);setContentAreaFilled(false);setBorder(new EmptyBorder(16,8,16,8));setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}
        protected void paintComponent(Graphics g){Graphics2D p=(Graphics2D)g.create();p.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);Color c=secondary?MINT:PURPLE;p.setColor(getModel().isPressed()?c.darker():getModel().isRollover()?new Color(secondary?208:95,secondary?231:52,secondary?217:192):c);p.fillRoundRect(0,0,getWidth(),getHeight(),14,14);p.dispose();super.paintComponent(g);}
    }
    static JButton link(String text){JButton b=new JButton(text);b.setFont(font(11,Font.BOLD));b.setForeground(PURPLE);b.setContentAreaFilled(false);b.setBorder(new EmptyBorder(6,0,6,0));b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));return b;}
    static void fieldLabel(JPanel p,String name,Entry entry){item(p,label(name,12,Font.BOLD,INK));gap(p,8);item(p,entry);}
    static boolean emailValid(String s){return s.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");}
    static JLabel status(){JLabel l=label(" ",11,Font.PLAIN,new Color(171,45,67));l.setPreferredSize(new Dimension(328,30));l.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));l.getAccessibleContext().setAccessibleName("İşlem sonucu");return l;}
    static void message(JLabel l,String text,boolean ok){l.setForeground(ok?new Color(45,111,68):new Color(171,45,67));l.setText(text);}
    static final class LoginPanel extends Card {
        final Entry emailEntry=new Entry("E-posta",false),passwordEntry=new Entry("Şifre",true);
        final JTextField email=emailEntry.field;final JPasswordField password=(JPasswordField)passwordEntry.field;
        final JLabel status=status();final JButton submit=new ActionButton("Giriş yap  →",false);final JCheckBox remember=new JCheckBox("E-postamı hatırla");
        LoginPanel(Screen screen){
            item(this,label("Hoş geldin.",26,Font.BOLD,INK));gap(this,8);item(this,label("Sana ayrılan zamana bir adım kaldı.",12,Font.PLAIN,MUTED));gap(this,27);
            fieldLabel(this,"E-posta",emailEntry);gap(this,19);fieldLabel(this,"Şifre",passwordEntry);gap(this,12);
            JPanel options=new JPanel(new BorderLayout());options.setOpaque(false);options.setMaximumSize(new Dimension(Integer.MAX_VALUE,32));remember.setOpaque(false);remember.setFont(font(11,Font.PLAIN));remember.setForeground(MUTED);remember.setToolTipText("Yalnızca e-posta adresini bu cihazda hatırlar.");JButton forgot=link("Şifremi unuttum");options.add(remember,BorderLayout.WEST);options.add(forgot,BorderLayout.EAST);item(this,options);gap(this,4);item(this,status);gap(this,7);
            JPanel buttons=new JPanel(new GridLayout(1,2,10,0));buttons.setOpaque(false);buttons.setMaximumSize(new Dimension(Integer.MAX_VALUE,53));buttons.setPreferredSize(new Dimension(328,53));buttons.add(submit);JButton register=new ActionButton("Kayıt ol",true);buttons.add(register);item(this,buttons);
            add(Box.createVerticalGlue());gap(this,17);
            JPanel foot=new JPanel(new BorderLayout(10,0));foot.setOpaque(false);foot.setMaximumSize(new Dimension(Integer.MAX_VALUE,46));foot.setBorder(new MatteBorder(1,0,0,0,new Color(239,235,245)));JLabel badge=label("B",16,Font.BOLD,PURPLE);foot.add(badge,BorderLayout.WEST);foot.add(label("Kendine ayırdığın zaman burada başlar.",10,Font.PLAIN,MUTED));item(this,foot);
            try{email.setText(java.util.prefs.Preferences.userNodeForPackage(BarberApp.class).get("email",""));remember.setSelected(!email.getText().isEmpty());}catch(SecurityException ignored){}
            register.addActionListener(e->screen.page("register"));submit.addActionListener(e->signIn());email.addActionListener(e->password.requestFocusInWindow());password.addActionListener(e->submit.doClick());forgot.addActionListener(e->JOptionPane.showMessageDialog(this,"Şifre sıfırlama servisi henüz bağlı değil.","Şifremi unuttum",JOptionPane.INFORMATION_MESSAGE));
        }
        void signIn(){char[] secret=password.getPassword();try{String address=email.getText().trim();if(address.isEmpty()){message(status,"E-posta adresini gir.",false);email.requestFocusInWindow();return;}if(!emailValid(address)){message(status,"Geçerli bir e-posta adresi gir.",false);email.requestFocusInWindow();return;}if(secret.length==0){message(status,"Şifreni gir.",false);password.requestFocusInWindow();return;}if(!AuthService.authenticate(address,secret)){message(status,"E-posta veya şifre hatalı.",false);return;}
            try{java.util.prefs.Preferences prefs=java.util.prefs.Preferences.userNodeForPackage(BarberApp.class);if(remember.isSelected())prefs.put("email",address);else prefs.remove("email");}catch(SecurityException ignored){}
            message(status,"Giriş başarılı. Hoş geldin!",true);password.setText("");
        }finally{Arrays.fill(secret,'\0');}}
    }
    static final class RegisterPanel extends Card {
        final Entry name=new Entry("Ad soyad",false),email=new Entry("E-posta",false),password=new Entry("Şifre",true),confirm=new Entry("Şifre tekrar",true);
        final JLabel status=status();final JButton submit=new ActionButton("Hesap oluştur  →",false);
        RegisterPanel(Screen screen){
            JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);top.setMaximumSize(new Dimension(Integer.MAX_VALUE,34));top.add(label("Aramıza katıl.",24,Font.BOLD,INK));JButton back=link("← Giriş yap");top.add(back,BorderLayout.EAST);item(this,top);gap(this,18);
            fieldLabel(this,"Ad soyad",name);gap(this,12);fieldLabel(this,"E-posta",email);gap(this,12);fieldLabel(this,"Şifre · en az 8 karakter",password);gap(this,12);fieldLabel(this,"Şifre tekrar",confirm);gap(this,8);item(this,status);gap(this,6);submit.setMaximumSize(new Dimension(Integer.MAX_VALUE,52));item(this,submit);
            back.addActionListener(e->screen.page("login"));submit.addActionListener(e->{char[] a=((JPasswordField)password.field).getPassword(),b=((JPasswordField)confirm.field).getPassword();try{
                String error=null;if(name.field.getText().trim().isEmpty())error="Adını ve soyadını gir.";else if(!emailValid(email.field.getText().trim()))error="Geçerli bir e-posta adresi gir.";else if(a.length<8)error="Şifre en az 8 karakter olmalı.";else if(!Arrays.equals(a,b))error="Şifreler eşleşmiyor.";else if(!AuthService.register(email.field.getText(),a))error="Bu e-posta zaten kayıtlı.";
                if(error!=null){message(status,error,false);return;}screen.login.email.setText(email.field.getText().trim());screen.login.password.setText("");password.field.setText("");confirm.field.setText("");message(screen.login.status,"Hesabın hazır. Şimdi giriş yapabilirsin.",true);screen.page("login");
            }finally{Arrays.fill(a,'\0');Arrays.fill(b,'\0');}});confirm.field.addActionListener(e->submit.doClick());
        }
    }
    static void layoutTree(Container c){c.doLayout();for(Component child:c.getComponents())if(child instanceof Container)layoutTree((Container)child);}
    public static void main(String[] args)throws Exception{
        if(args.length>0&&args[0].equals("--preview")){SwingUtilities.invokeAndWait(()->{try{Screen s=new Screen();s.setSize(420,860);layoutTree(s);for(String page:new String[]{"login","register"}){s.page(page);layoutTree(s);BufferedImage im=new BufferedImage(420,860,BufferedImage.TYPE_INT_RGB);Graphics2D g=im.createGraphics();s.printAll(g);g.dispose();ImageIO.write(im,"png",new File("docs/BARBER-"+page+".png"));}}catch(IOException e){throw new RuntimeException(e);}});return;}
        SwingUtilities.invokeLater(()->{JFrame f=new JFrame("BARBER");Screen s=new Screen();f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);f.setContentPane(s);f.pack();f.setResizable(false);f.setLocationRelativeTo(null);f.getRootPane().setDefaultButton(s.login.submit);f.setVisible(true);});
    }
}
