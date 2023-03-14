<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="productMainAttribute">
        <h2 class="jh-entity-heading" data-cy="productMainAttributeDetailsHeading">
          <span v-text="$t('ecommercyApp.productMainAttribute.detail.title')">ProductMainAttribute</span> {{ productMainAttribute.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('ecommercyApp.productMainAttribute.price')">Price</span>
          </dt>
          <dd>
            <span>{{ productMainAttribute.price }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.productMainAttribute.discount')">Discount</span>
          </dt>
          <dd>
            <span>{{ productMainAttribute.discount }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.productMainAttribute.attribute')">Attribute</span>
          </dt>
          <dd>
            <div v-if="productMainAttribute.attribute">
              <router-link :to="{ name: 'AttributeView', params: { attributeId: productMainAttribute.attribute.id } }">{{
                productMainAttribute.attribute.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="productMainAttribute.id"
          :to="{ name: 'ProductMainAttributeEdit', params: { productMainAttributeId: productMainAttribute.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./product-main-attribute-details.component.ts"></script>
