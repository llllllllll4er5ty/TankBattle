package com.leicx.map;

import java.awt.Color;

/**
 *@author leicx 
 *@time 2018��1��4��
 */
public class MapObject{
	//Ӳ��
	public int hardness = 0;
	//��ɫ
	public Color color = null;
	//�Ƿ���ʵ����
	public boolean exist = true;
	//�ܷ�tank������Ĭ�ϲ�����
	public boolean canBeTankThrough = false;
	//�ܷ��ӵ�������Ĭ�ϲ�����
	public boolean canBeBulletThrough = false;
	//�õ����������
	public String getName() {
		return this.getClass().getSimpleName();
	}
	/**
	 * �õ���������
	 */
	public String getCompleteName() {
		return this.getClass().getName();
	}
}
