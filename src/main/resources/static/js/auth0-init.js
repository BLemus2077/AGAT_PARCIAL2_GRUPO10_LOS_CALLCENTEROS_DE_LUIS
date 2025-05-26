async function initializeToken() {
  if (sessionStorage.getItem("access_token")) return;

  try {
    const res = await fetch("/api/user-token");
    if (!res.ok) throw new Error("No se pudo obtener el token");

    const token = await res.text();
    sessionStorage.setItem("access_token", token);
    console.log("Token JWT guardado en sessionStorage");
  } catch (e) {
    console.error("Error obteniendo el token desde el backend:", e);
  }
}
