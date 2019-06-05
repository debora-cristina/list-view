package com.br.debora.dao;

import java.util.ArrayList;
import java.util.List;

import com.br.debora.vo.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO {
	private SQLiteDatabase database;
	private CriarBanco baseDAO;
	
	private String[] columns = {CriarBanco.USUARIO_ID,
								CriarBanco.USUARIO_NOME,
								CriarBanco.USUARIO_CPF,
								CriarBanco.USUARIO_TELEFONE,
								CriarBanco.USUARIO_EMAIL};

	
	public UsuarioDAO(Context context) {
		baseDAO = new CriarBanco(context);
	}
	
	
	public void open() throws SQLException {
		database = baseDAO.getWritableDatabase();
	}
	
	
	public void close() {
		baseDAO.close();
	}
	
	
	public long create(Usuario user) {
		ContentValues values = new ContentValues();

		values.put(CriarBanco.USUARIO_NOME, user.getNome());
		values.put(CriarBanco.USUARIO_CPF, user.getCpf());
		values.put(CriarBanco.USUARIO_TELEFONE, user.getTelefone());
		values.put(CriarBanco.USUARIO_EMAIL, user.getEmail());

		return database.insert(CriarBanco.TABLE_USUARIO, null, values);
	}
	
	
	public Usuario read(long id) {
		Cursor cursor = database.query(CriarBanco.TABLE_USUARIO, columns, CriarBanco.USUARIO_ID + " = " + id, null, null, null, null);
		Usuario user = new Usuario();
		if  (cursor.moveToFirst()) {
			user.setId(cursor.getLong(0));
			user.setNome(cursor.getString(1));
			user.setCpf(cursor.getString(2));
			user.setTelefone(cursor.getString(3));
			user.setEmail(cursor.getString(4));
		}
		cursor.close();
		return user;
	}
	
	
	public List<Usuario> readAll() {
		List<Usuario> users = new ArrayList<Usuario>();
		Cursor cursor = database.query(CriarBanco.TABLE_USUARIO, columns, null, null, null, null, null);
		if  (cursor.moveToFirst()) {
			while(cursor.isAfterLast()) {
				Usuario user = new Usuario( cursor.getLong(0),
						cursor.getString(1),
						cursor.getString(2),
						cursor.getString(3),
						cursor.getString(4));
				users.add(user);
				cursor.moveToNext();
			}
		}
		cursor.close();
		return users;
	}
	
	
	public int update(Usuario user) {
		long id = user.getId();
		ContentValues values = new ContentValues();
		
		values.put(CriarBanco.USUARIO_NOME, user.getNome());
		values.put(CriarBanco.USUARIO_CPF, user.getCpf());
		values.put(CriarBanco.USUARIO_TELEFONE, user.getTelefone());
		values.put(CriarBanco.USUARIO_EMAIL, user.getEmail());
		return database.update(CriarBanco.TABLE_USUARIO, values, CriarBanco.USUARIO_ID + " = " + id, null);
	}
	
	
	public void delete(Usuario user) {
		long id = user.getId();
		
		database.delete(CriarBanco.TABLE_USUARIO, CriarBanco.USUARIO_ID + " = " + id, null);
	}
	
}
