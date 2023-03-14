<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecommercyApp.address.home.createOrEditLabel"
          data-cy="AddressCreateUpdateHeading"
          v-text="$t('ecommercyApp.address.home.createOrEditLabel')"
        >
          Create or edit a Address
        </h2>
        <div>
          <div class="form-group" v-if="address.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="address.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.address.street1')" for="address-street1">Street 1</label>
            <input
              type="text"
              class="form-control"
              name="street1"
              id="address-street1"
              data-cy="street1"
              :class="{ valid: !$v.address.street1.$invalid, invalid: $v.address.street1.$invalid }"
              v-model="$v.address.street1.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.address.street2')" for="address-street2">Street 2</label>
            <input
              type="text"
              class="form-control"
              name="street2"
              id="address-street2"
              data-cy="street2"
              :class="{ valid: !$v.address.street2.$invalid, invalid: $v.address.street2.$invalid }"
              v-model="$v.address.street2.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.address.unit')" for="address-unit">Unit</label>
            <input
              type="text"
              class="form-control"
              name="unit"
              id="address-unit"
              data-cy="unit"
              :class="{ valid: !$v.address.unit.$invalid, invalid: $v.address.unit.$invalid }"
              v-model="$v.address.unit.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.address.zipCode')" for="address-zipCode">Zip Code</label>
            <input
              type="text"
              class="form-control"
              name="zipCode"
              id="address-zipCode"
              data-cy="zipCode"
              :class="{ valid: !$v.address.zipCode.$invalid, invalid: $v.address.zipCode.$invalid }"
              v-model="$v.address.zipCode.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.address.phoneNumber')" for="address-phoneNumber">Phone Number</label>
            <input
              type="text"
              class="form-control"
              name="phoneNumber"
              id="address-phoneNumber"
              data-cy="phoneNumber"
              :class="{ valid: !$v.address.phoneNumber.$invalid, invalid: $v.address.phoneNumber.$invalid }"
              v-model="$v.address.phoneNumber.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.address.longitude')" for="address-longitude">Longitude</label>
            <input
              type="number"
              class="form-control"
              name="longitude"
              id="address-longitude"
              data-cy="longitude"
              :class="{ valid: !$v.address.longitude.$invalid, invalid: $v.address.longitude.$invalid }"
              v-model.number="$v.address.longitude.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.address.latitude')" for="address-latitude">Latitude</label>
            <input
              type="number"
              class="form-control"
              name="latitude"
              id="address-latitude"
              data-cy="latitude"
              :class="{ valid: !$v.address.latitude.$invalid, invalid: $v.address.latitude.$invalid }"
              v-model.number="$v.address.latitude.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.address.customer')" for="address-customer">Customer</label>
            <select class="form-control" id="address-customer" data-cy="customer" name="customer" v-model="address.customer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="address.customer && customerOption.id === address.customer.id ? address.customer : customerOption"
                v-for="customerOption in customers"
                :key="customerOption.id"
              >
                {{ customerOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.address.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./address-update.component.ts"></script>
