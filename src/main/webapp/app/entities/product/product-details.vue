<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="product">
        <h2 class="jh-entity-heading" data-cy="productDetailsHeading">
          <span v-text="$t('ecommercyApp.product.detail.title')">Product</span> {{ product.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('ecommercyApp.product.name')">Name</span>
          </dt>
          <dd>
            <span>{{ product.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.product.brand')">Brand</span>
          </dt>
          <dd>
            <span>{{ product.brand }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.product.rate')">Rate</span>
          </dt>
          <dd>
            <span>{{ product.rate }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.product.description')">Description</span>
          </dt>
          <dd>
            <span>{{ product.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.product.mainPic')">Main Pic</span>
          </dt>
          <dd>
            <div v-if="product.mainPic">
              <router-link :to="{ name: 'PictureView', params: { pictureId: product.mainPic.id } }">{{ product.mainPic.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.product.productMainAttribute')">Product Main Attribute</span>
          </dt>
          <dd>
            <div v-if="product.productMainAttribute">
              <router-link
                :to="{ name: 'ProductMainAttributeView', params: { productMainAttributeId: product.productMainAttribute.id } }"
                >{{ product.productMainAttribute.id }}</router-link
              >
            </div>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.product.attribute')">Attribute</span>
          </dt>
          <dd>
            <span v-for="(attribute, i) in product.attributes" :key="attribute.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'AttributeView', params: { attributeId: attribute.id } }">{{ attribute.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="product.id" :to="{ name: 'ProductEdit', params: { productId: product.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./product-details.component.ts"></script>
