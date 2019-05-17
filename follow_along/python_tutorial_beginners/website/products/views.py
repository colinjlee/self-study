from django.http import HttpResponse
from .models import Product
from django.shortcuts import render


def index(request):
    products = Product.objects.all()
    return render(request, "index.html", {"products": products})


def new(request):
    return HttpResponse("New Products")

