<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="address">
        <h2 class="jh-entity-heading" data-cy="addressDetailsHeading">
          <span v-text="$t('ecommercyApp.address.detail.title')">Address</span> {{ address.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('ecommercyApp.address.street1')">Street 1</span>
          </dt>
          <dd>
            <span>{{ address.street1 }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.address.street2')">Street 2</span>
          </dt>
          <dd>
            <span>{{ address.street2 }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.address.unit')">Unit</span>
          </dt>
          <dd>
            <span>{{ address.unit }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.address.zipCode')">Zip Code</span>
          </dt>
          <dd>
            <span>{{ address.zipCode }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.address.phoneNumber')">Phone Number</span>
          </dt>
          <dd>
            <span>{{ address.phoneNumber }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.address.longitude')">Longitude</span>
          </dt>
          <dd>
            <span>{{ address.longitude }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.address.latitude')">Latitude</span>
          </dt>
          <dd>
            <span>{{ address.latitude }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.address.customer')">Customer</span>
          </dt>
          <dd>
            <div v-if="address.customer">
              <router-link :to="{ name: 'CustomerView', params: { customerId: address.customer.id } }">{{
                address.customer.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="address.id" :to="{ name: 'AddressEdit', params: { addressId: address.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./address-details.component.ts"></script>
