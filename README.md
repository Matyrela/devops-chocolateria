# Chocolatería – Prometheus + Grafana (Minikube)

Despliega:
- **ChocoApp** (Spring Boot)
- **Prometheus**
- **Grafana**

> Namespace: `devops-ucu`  
> Grafana: **admin / admin**

---

## Requisitos

- Minikube y kubectl instalados.

---

## Despliegue

Archivo: `kubernetes.yml`

```bash
kubectl apply -f kubernetes.yml
kubectl -n devops-ucu get pods -w
```

---

## Acceso

### Via NodePort
```bash
MINIKUBE_IP=$(minikube ip)

echo "Grafana:    http://$MINIKUBE_IP:30300"
echo "Prometheus: http://$MINIKUBE_IP:30900"
echo "ChocoApp:   http://$MINIKUBE_IP:30880/actuator/health"
```

### Via Port-forward
```bash
kubectl -n devops-ucu port-forward svc/grafana-svc     3000:3000 &
kubectl -n devops-ucu port-forward svc/prometheus-svc  9090:9090 &
kubectl -n devops-ucu port-forward svc/chocoapp-svc    8080:80   &
# Grafana:    http://localhost:3000
# Prometheus: http://localhost:9090
# ChocoApp:   http://localhost:8080/actuator/health
```

---

## Dashboards (Grafana → carpeta “Chocolatería”)

- **Chocolatería - Infraestructura**
    - Requests (30s)
    - Latencia p95 (30s)
    - Errores 5xx (30s)
    - CPU del proceso
    - JVM Heap usado (bytes)

- **Chocolatería - Órdenes**
    - Órdenes (POST `/api/orders`)
    - Órdenes por estado (2xx/4xx/5xx)
    - Latencia p95 de órdenes

---

## Re-ejecutar carga inicial

```bash
kubectl -n devops-ucu delete job chocoapp-loadgen-once --ignore-not-found
kubectl apply -f kubernetes.yml
kubectl -n devops-ucu logs job/chocoapp-loadgen-once -f
```

---

## Limpieza

```bash
kubectl delete namespace devops-ucu
```
