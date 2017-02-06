/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.santuario.seg.util;

import br.gov.to.santuario.seg.domain.Menu;
import java.util.List;
import java.util.Objects;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;


public class MenuUtil {

    public static MenuModel formarMenu(List<Menu> listaMenus) {        
        MenuModel modelMenu = new DefaultMenuModel();
        for (Menu listaMenu2 : listaMenus) {
            //System.out.println(listamenu.get(i).getDescricao() + " : " + listamenu.get(i).getId_pai().getId());
            if (listaMenu2.getPai().getId() == 1) {
                Menu menuModulo1 = listaMenu2;
                //Cria o MODULO NIVEL 1
                DefaultSubMenu modulo = new DefaultSubMenu(menuModulo1.getDescricao(), menuModulo1.getIcone());
                // Adiciona o link para 

                for (Menu listaMenu1 : listaMenus) {
                    if (Objects.equals(menuModulo1.getId(), listaMenu1.getPai().getId())) {
                        Menu menuItem2 = listaMenu1;
                        // Cria o serviço NIVEL 2                        
                        //DefaultSubMenu servico = new DefaultSubMenu(menuItem2.getDescricao(), menuItem2.getIcone());
                        DefaultMenuItem servico = new DefaultMenuItem(menuItem2.getDescricao(), menuItem2.getIcone(), menuItem2.getUrl());
                        servico.setUrl(menuItem2.getUrl());
                        // Adiciona no menu

                        modulo.addElement(servico);
//                        for (Menu listaMenu : listaMenus) {
//                            if (Objects.equals(menuItem2.getId(), listaMenu.getId_pai().getId())) {
//                                //Cria o Sub-serviço NIVEL 3
//                                Menu menuItem3 = listaMenu;
//                                DefaultMenuItem itemSubServico = new DefaultMenuItem(menuItem3.getDescricao(), menuItem3.getIcone(), menuItem3.getUrl());
//
//                                servico.addElement(itemSubServico);
//
//                            }
//                        }
                    }
                }
                modelMenu.addElement(modulo);
            }
        }
        return modelMenu;
    }

}
