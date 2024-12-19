se han creado desde zero:
-layout: activity denuncia (interfaz gráfica)
-modificado de activity_multi para poder entrar a la pantalla de denuncia
- se ha creado DenunciarActivity,  y el modelo denunciar con los requerimientos de fecha, título, description (message) y sender (lo coge de forma automática)
- Se ha creado un callback para denuncias, para poder mostrar mensages por pantalla si hay errores o qualquier cosa en la connexión con el servidor.
- finalmente se ha tocado la interfaz Servidor BBDD para poder llegar a nuestro destino en el servidory se le passa un Call<Void> ya que no se espera respuesta de este, solo un codigo.
- La denuncia acualmente se pasa como string debido a que la clase Denunciar no esta implementada en el servidor, aún así, funcionaria igual pasandole un objeto de clase denunciar, la respuesta del cual nos haría saltar una excepción en swagger ya que no encontraría la clase en cuestion por el momento
- Finalmente se ha hecho el post denuncia, el qual es el encargado de implementar la interfaz.
- Todo el mínimo 2 se ha completado.
- Solo quedaría por implementar (fuera del mínimo 2) la clase Denunciar en el servidor y hacer una conexion con la BBDD para guardar las denuncias de los diferentes usuarios.
